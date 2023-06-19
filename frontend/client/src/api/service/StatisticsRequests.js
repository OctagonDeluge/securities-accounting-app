import {axios} from "../axios";
import {API_URL} from "../../constants/API";
import {useState} from "react";
import {TypeConverterForStatistics} from "../../util/TypeConverter";

export function useSecurityTypeStatistics() {
    const [statistics, setStatistics] = useState([]);

    const getStatistics = (portfolioId) => {
        axios
            .get(`${API_URL}/statistics/portfolio/${portfolioId}`)
            .then(response => {
                const temp = [];
                response.data.forEach(value => temp.push({name: TypeConverterForStatistics(value.type), value: value.count}));
                setStatistics(temp);
            });
    };

    return {  getStatistics, statistics };
}