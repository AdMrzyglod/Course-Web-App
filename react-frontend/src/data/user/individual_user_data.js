import {fetchIndividualUserAccountData} from "../../service/user/individual_user_service";


export const getIndividualUserAccountData = async () => {

    try {

        const response = await fetchIndividualUserAccountData();
        return response.data;

    } catch (error) {
        throw error
    }
};
