import {useEffect, useState} from "react";
import {axios} from "../axios";
import {API_URL} from "../../constants/API";

export function useExchangeSearch(query) {
    const [securities, setSecurities] = useState([]);

    useEffect(() => {
        request(query);
    }, [query])

    const request = (query) => {
        axios
            .get(`${API_URL}/exchange/moex/securities`, {
                params: {
                    securityName: query
                }
            })
            .then(response => {
                setSecurities(response.data);
            })
    };

    return { securities }
}

export function useExchangePrices() {
    const [price, setPrice] = useState(0);


    const request = (secid, securityType) => {
        axios
            .get(`${API_URL}/exchange/moex/price/${secid}`, {
                params: {
                    securityType: securityType
                }
            })
            .then(response => {
                setPrice(response.data);
            })
    }

    return { price, request }
}