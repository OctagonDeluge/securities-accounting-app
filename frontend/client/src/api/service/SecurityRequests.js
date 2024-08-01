import {axios} from "../axios";
import {API_URL} from "../../constants/API";
import {useState} from "react";

export function SecurityService() {
    const [securities, setSecurities] = useState([]);

    const getSecurities = (portfolioId) => {
        axios
            .get(`${API_URL}/security/portfolio/${portfolioId}`)
            .then(response => {
                setSecurities(response.data);
            });
    }

    return { getSecurities, securities };
}