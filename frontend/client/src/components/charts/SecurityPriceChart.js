import React, {useEffect, useState} from "react";
import {axios} from "../../api/axios";
import {API_URL} from "../../constants/API";
import {PriceChart} from "./PriceChart";
import {SegmentedControl} from "@mantine/core";
import "../../assets/styles/ChartStyles.css"
import {showNotification} from "@mantine/notifications";
import {IconCircleX} from "@tabler/icons";

const beginDate = new Date().setHours(0, 0, 0, 1);
const endDate = new Date().setHours(23, 59, 59, 999);
const countDays = (days) => {
    return 24 * days * 60 * 60 * 1000;
}

export function SecurityPriceChart({entity}) {
    const [prices, setPrices] = useState([]);
    const [days, setDays] = useState([beginDate - countDays(3), 10]);

    const namings = {
        x: "end",
        y: "close",
        line: "close"
    }

    useEffect(() => {
        if (entity.secid !== "") {
            getPrices(beginDate - countDays(3), endDate, 10, entity.group);
        }
    }, [entity])

    const getPrices = (from, till, timeInterval, securityType) => {
        axios
            .get(`${API_URL}/statistics/exchange/${entity.exchangeName}/security/${entity.secid}`, {
                params: {
                    securityType: securityType,
                    fromDate: from,
                    tillDate: till,
                    dayInterval: timeInterval
                }
            })
            .then(response => {
                setPrices(response.data)
            })
            .catch(response => {
                showNotification({
                    autoClose: 5000,
                    title: "Ошибка",
                    message: response,
                    color: 'red',
                    icon: <IconCircleX/>,
                })
            })
    }

    return (
        <div className="chart">
            <PriceChart data={prices} namings={namings}/>
            <SegmentedControl
                value={days}
                fullWidth={true}
                onChange={value => {setDays(value); getPrices(value[0], endDate, value[1], entity.group);}}
                data={[
                    {label: '1 день', value: [beginDate, 1]},
                    {label: '3 дня', value: [beginDate - countDays(3), 10]},
                    {label: '1 неделя', value: [beginDate - countDays(7), 60]},
                    {label: '1 месяц', value: [beginDate - countDays(30), 60]},
                    {label: '6 месяцев', value: [beginDate - countDays(180), 24]},
                    {label: '1 год', value: [beginDate - countDays(365), 7]},
                ]}/>
        </div>
    )
}