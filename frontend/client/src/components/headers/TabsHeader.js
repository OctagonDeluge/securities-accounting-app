import React, {useState} from "react";
import {Tabs} from "@mantine/core";
import {useNavigate, useParams} from "react-router-dom";

export function TabsHeader({tab}) {
    const [activeTab, setActiveTab] = useState(tab);
    const navigate = useNavigate();
    const {portfolioId} = useParams();

    return (
        <Tabs value={activeTab} onTabChange={value => {
            setActiveTab(value);
            navigate(`/portfolio/${portfolioId}/${value}`);
        }}>
            <Tabs.List>
                <Tabs.Tab value="security">Ценные бумаги</Tabs.Tab>
                <Tabs.Tab value="exchange">Поиск</Tabs.Tab>
            </Tabs.List>
        </Tabs>
    )
}