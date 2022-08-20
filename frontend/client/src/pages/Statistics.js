import React, {useEffect, useState} from "react";
import axios from "axios";
import {API_URL} from "../constants/API";
import {useParams} from "react-router-dom";
import {TypeConverterForStatistics} from "../util/TypeConverter";
import "../assets/styles/StatisticsStyles.css"
import {SecurityTypeChart} from "../components/charts/SecurityTypeChart";
import {Title} from "@mantine/core";
import {TabsHeader} from "../components/headers/TabsHeader";

export function Statistics() {
    const {portfolioId} = useParams();
    const [statistics, setStatistics] = useState([]);
    const namings = {
        dataKey: "value",
        nameKey: "name"
    }

    useEffect(() => {
        axios
            .get(`${API_URL}/statistics/portfolio/${portfolioId}`)
            .then(res => {
                const temp = [];
                res.data.forEach(value => temp.push({name: TypeConverterForStatistics(value.type), value: value.count}));
                setStatistics(temp);
            })
    }, [])

    return (
        <div className="statistics">
            <TabsHeader tab={"statistics"}/>
            <div className="pieChartSection">
                <Title>Типы бумаг</Title>
                <SecurityTypeChart data={statistics} namings={namings}/>
            </div>
        </div>
    )
}