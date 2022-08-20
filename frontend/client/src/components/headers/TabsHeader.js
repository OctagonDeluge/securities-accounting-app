import React, {useEffect, useState} from "react";
import {Tabs} from "@mantine/core";
import {useNavigate, useParams} from "react-router-dom";

export function TabsHeader({tab}) {
    const [activeTab, setActiveTab] = useState(tab);
    const navigate = useNavigate();
    const {portfolioId} = useParams();

    useEffect(() => {
        navigate(`/portfolio/${portfolioId}/${activeTab}`)
    }, [activeTab])

    return (
        <Tabs value={activeTab} onTabChange={setActiveTab}>
            <Tabs.List>
                <Tabs.Tab value="security">Ценные бумаги</Tabs.Tab>
                <Tabs.Tab value="exchange">Поиск</Tabs.Tab>
                <Tabs.Tab value="statistics">Статистика</Tabs.Tab>
            </Tabs.List>
        </Tabs>
    )
}