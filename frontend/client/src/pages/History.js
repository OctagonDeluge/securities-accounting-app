import React, {useEffect, useState} from "react";
import {Table, Title} from "@mantine/core";
import {PurchaseInfoCard} from "../components/cards/PurchaseInfoCard";
import {OperationCard} from "../components/cards/OperationCard";
import {axios} from "../api/axios";
import {API_URL} from "../constants/API";
import "../assets/styles/HistoryStyles.css"

export function History() {
    const [operations, setOperations] = useState([]);

    useEffect(() => {
        axios
            .get(`${API_URL}/history`)
            .then(response => {
                setOperations(response.data);
            })
    },[])

    return (
        <div className="history">
            <Title order={4}>История проведенных операций</Title>
            <Table highlightOnHover={true} sx={{width: "90%"}} verticalSpacing="xs">
                <thead>
                <tr>
                    <th>Идентификатор</th>
                    <th>Название</th>
                    <th>Тип операции</th>
                    <th>Количество</th>
                    <th>Стоимость</th>
                    <th>Итог</th>
                    <th>Прибыль</th>
                    <th>Валюта</th>
                    <th>Дата</th>
                </tr>
                </thead>
                <tbody>
                {
                    operations.map(operation => (
                        <OperationCard key={operation.id} operation={operation} />
                    ))
                }
                </tbody>
            </Table>
        </div>
    )
}