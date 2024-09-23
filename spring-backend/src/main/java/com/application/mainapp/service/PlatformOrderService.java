package com.application.mainapp.service;


import com.application.mainapp.dto.platformorder.*;
import com.application.mainapp.model.*;
import com.application.mainapp.repository.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlatformOrderService {

    private final PlatformOrderRepository platformOrderRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final IndividualUserRepository individualUserRepository;
    private final CourseRepository courseRepository;
    private final CourseCodeRepository courseCodeRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public PlatformOrderService(PlatformOrderRepository platformOrderRepository, OrderDetailsRepository orderDetailsRepository, IndividualUserRepository individualUserRepository, CourseRepository courseRepository, CourseCodeRepository courseCodeRepository, ModelMapper modelMapper) {
        this.platformOrderRepository = platformOrderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.individualUserRepository = individualUserRepository;
        this.courseRepository = courseRepository;
        this.courseCodeRepository = courseCodeRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void saveOrder(Long platformUserID, PlatformOrderCreateDTO platformOrderCreateDTO){
        Optional<IndividualUser> userOptional = this.individualUserRepository.findById(platformUserID);
        if(userOptional.isEmpty()){
            throw new IllegalArgumentException("PlatformUser not exist");
        }
        if(platformOrderCreateDTO.getOrderDetailsCreateDTOList().size()==0){
            throw new RuntimeException("OrderDetails is empty");
        }

        IndividualUser individualUser = userOptional.get();

        PlatformOrder platformOrder = new PlatformOrder();
        platformOrder.setIndividualUser(individualUser);

        platformOrder = this.platformOrderRepository.save(platformOrder);
        PlatformOrder finalPlatformOrder = platformOrder;

        BigDecimal orderPrice = BigDecimal.ZERO;

        for(OrderDetailsCreateDTO details: platformOrderCreateDTO.getOrderDetailsCreateDTOList()) {
                    if(details.getQuantity()<=0){
                        throw new IllegalArgumentException("Invalid quantity");
                    }

                    Optional<Course> courseOptional = this.courseRepository.findById(details.getCourseID());
                    if(courseOptional.isEmpty()){
                        throw new IllegalArgumentException("Course not exists");
                    }

                    Course course = courseOptional.get();
                    orderPrice = orderPrice.add(BigDecimal.valueOf(details.getQuantity()).multiply(course.getPrice()));

                    List<CourseCode> courseCodeList = this.courseCodeRepository.findCourseCodesByOrderDetailsIsNullAndCourseId(details.getCourseID());

                    if(courseCodeList.size()<details.getQuantity()){
                        throw new RuntimeException("Not enough codes");
                    }

                    courseCodeList.subList(0,details.getQuantity())
                            .forEach(code->{
                                OrderDetails orderDetail = new OrderDetails();
                                orderDetail.setCoursePrice(course.getPrice());
                                orderDetail.setPlatformOrder(finalPlatformOrder);
                                orderDetail.setCourseCode(code);

                                this.orderDetailsRepository.save(orderDetail);
                            });
        }

        if(individualUser.getMoney().subtract(orderPrice).compareTo(BigDecimal.ZERO)>=0){
            individualUser.setMoney(individualUser.getMoney().subtract(orderPrice));
        }
        else{
            throw new RuntimeException("User does not have enough money");
        }

        this.individualUserRepository.save(individualUser);
    }

    public List<PlatformUserOrderListResponseDTO> getAllOrders(){
        List<PlatformUserOrderListResponseDTO> platformOrderListResponseDTOList = this.platformOrderRepository.findAllPlatformUserOrderListResponseDTO();

        platformOrderListResponseDTOList.forEach(order->{
            order.setOrderCourses(this.platformOrderRepository.findPlatformOrderCourseResponseDTObyPlatformOrderId(order.getPlatformOrderID()));
        });

        return platformOrderListResponseDTOList;
    }

    public Optional<PlatformOrderResponseDTO> getOrderById(Long id) {
        Optional<PlatformOrder> orderOptional = this.platformOrderRepository.findById(id);

        if (orderOptional.isPresent()) {
            PlatformOrder order = orderOptional.get();
            PlatformOrderResponseDTO platformOrderResponseDTO = modelMapper.map(order, PlatformOrderResponseDTO.class);
            platformOrderResponseDTO.setTotalPrice(this.platformOrderRepository.platformOrderTotalPrice(id));
            return Optional.of(platformOrderResponseDTO);
        } else {
            return Optional.empty();
        }
    }
    public List<PlatformOrderResponseDTO> getIndividualUserOrders(Long id){
        List<PlatformOrder> orders = this.platformOrderRepository.findPlatformOrderByPlatformUserId(id);
        return orders.stream()
                .map(order -> {
                    PlatformOrderResponseDTO platformOrderResponseDTO = this.modelMapper.map(order, PlatformOrderResponseDTO.class);
                    platformOrderResponseDTO.setTotalPrice(this.platformOrderRepository.platformOrderTotalPrice(order.getPlatformOrderID()));
                    return platformOrderResponseDTO;
                })
                .collect(Collectors.toList());
    }


    public List<PlatformOrderListResponseDTO> getOrdersWithCourses(Long platformUserID){
        List<PlatformOrderListResponseDTO> platformOrderListResponseDTOList = this.platformOrderRepository.findPlatformOrderListResponseDTO(platformUserID);

        platformOrderListResponseDTOList.forEach(order->{
            order.setOrderCourses(this.platformOrderRepository.findPlatformOrderCourseResponseDTObyPlatformOrderId(order.getPlatformOrderID()));
        });

        return platformOrderListResponseDTOList;
    }

    public List<PlatformOrderCourseCodeInfoResponseDTO> getOrderCourseCodes(Long orderID, Long courseID, Long platformUserID){
        return this.platformOrderRepository.findPlatformOrderCourseCodeInfoResponseDTObyPlatformOrderIdAndCourseIdAndUserId(orderID, courseID, platformUserID);
    }

    public List<PlatformOrderCourseCodeInfoResponseDTO> getOrderCourseCodes(Long orderID, Long courseID){
        return this.platformOrderRepository.findPlatformOrderCourseCodeInfoResponseDTObyPlatformOrderIdAndCourseId(orderID, courseID);
    }

    @Transactional
    public void deletePlatformOrder(Long id) {
        Optional<PlatformOrder> platformOrderOptional = this.platformOrderRepository.findById(id);
        if(platformOrderOptional.isPresent()){
            IndividualUser individualUser = platformOrderOptional.get().getIndividualUser();
            individualUser.setMoney(individualUser.getMoney().add(this.platformOrderRepository.platformOrderTotalPrice(id)));
            this.individualUserRepository.save(individualUser);
            this.platformOrderRepository.deleteById(id);
        }
    }
}
