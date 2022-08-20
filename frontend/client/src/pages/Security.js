import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";
import {SecurityCard} from "../components/cards/SecurityCard";
import "../assets/styles/SecuritiesStyle.css"
import {TabsHeader} from "../components/headers/TabsHeader";
import {API_URL} from "../constants/API";

export function Security() {
    const [securities, setSecurities] = useState([]);
    const {portfolioId} = useParams();

    useEffect(() => {
        axios
            .get(`${API_URL}/security/portfolio/${portfolioId}`)
            .then((response) => {
                setSecurities(response.data);
            })
    }, [])

    return (
        <div className="security">
            <TabsHeader tab={"security"}/>
            <div className="securities">
                {
                    securities.map(security => (
                        <SecurityCard key={security.id} security={security}/>
                    ))
                }
            </div>
        </div>
    )
}