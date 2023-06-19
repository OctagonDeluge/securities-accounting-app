import React, {useEffect} from "react";
import {useParams} from "react-router-dom";
import "../assets/styles/StatisticsStyles.css"
import {SecurityTypeChart} from "../components/charts/SecurityTypeChart";
import {Title} from "@mantine/core";
import {TabsHeader} from "../components/headers/TabsHeader";
import {useSecurityTypeStatistics} from "../api/service/StatisticsRequests";

export function Statistics() {
    const {portfolioId} = useParams();
    const statisticsService = useSecurityTypeStatistics();
    const namings = {
        dataKey: "value",
        nameKey: "name"
    }

    useEffect(() => {
        statisticsService.getStatistics(portfolioId);
    }, [])

    return (
        <div className="statistics">
            <TabsHeader tab={"statistics"}/>
            <div className="pieChartSection">
                <Title>Типы бумаг</Title>
                <SecurityTypeChart data={statisticsService.statistics} namings={namings}/>
            </div>
        </div>
    )
}