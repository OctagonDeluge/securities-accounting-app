import React, {useEffect} from "react";
import {useParams} from "react-router-dom";
import {SecurityCard} from "../components/cards/SecurityCard";
import "../assets/styles/SecuritiesStyle.css"
import {TabsHeader} from "../components/headers/TabsHeader";
import {SecurityService} from "../api/service/SecurityRequests";

export function Security() {
    const service =
        SecurityService();
    const {portfolioId} = useParams();

    useEffect(() => {
        service.getSecurities(portfolioId);
    }, [])

    return (
        <div className="security">
            <TabsHeader tab={"security"}/>
            <div className="securities">
                {
                    service.securities.map(security => (
                        <SecurityCard key={security.id} security={security}/>
                    ))
                }
            </div>
        </div>
    )
}