import React, {useEffect, useState} from "react";
import axios from "axios";
import "../assets/styles/PortfoliosStyle.css"
import "../assets/styles/IconStyles.css"
import {TextInput, ScrollArea, Table} from "@mantine/core";
import {showNotification} from "@mantine/notifications";
import {API_URL} from "../constants/API";
import {PortfolioCard} from "../components/cards/PortfolioCard";
import {IconCirclePlus, IconBriefcase} from "@tabler/icons"

export function Portfolio() {
    const [portfolios, setPortfolios] = useState([]);
    const [name, setName] = useState("Новый портфель");

    useEffect(() => {
        loadPortfolios();
    }, [])

    const deletePortfolio = (id) => {
        axios
            .delete(`${API_URL}/portfolio/${id}`)
            .then(res => {
                let clone = [...portfolios].filter(item => item.id !== id);
                setPortfolios(clone);
            })
    }

    const updatePortfolio = (id, oldName, newName, setName) => {
        axios
            .put(`${API_URL}/portfolio/${id}`, {}, {
                params: {
                    portfolioName: newName
                }
            })
            .then(res => {
                let clone = [...portfolios].filter(item => item.id !== id);
                clone.push(res.data);
                setPortfolios(clone);
            })
            .catch(res => {
                setName(oldName);
                let text = "";
                res.response.data.map(resp => text += resp.fieldName + " " + resp.message);
                showNotification({
                    title: "Не удалось обновить портфель",
                    message: text,
                    color: "red"
                })
            })
    }

    const addPortfolio = (name) => {
        axios
            .post(`${API_URL}/portfolio`, {}, {
                params: {
                    portfolioName: name
                }
            })
            .then(response => {
                setPortfolios([...portfolios, response.data]);
            })
            .catch(res => {
                let text = "";
                res.response.data.map(resp => text += resp.fieldName + " " + resp.message);
                showNotification({
                    title: "Не удалось удалить портфель",
                    message: text,
                    color: "red"
                })
            })
    }

    const loadPortfolios = () => {
        axios
            .get("http://localhost:8080/portfolio")
            .then(response => {
                setPortfolios(response.data)
            })
    }

    return (
        <div className="portfolioMain">
            <div className="portfolios">
                <ScrollArea>
                    <Table sx={{minWidth: 800}} verticalSpacing="xs">
                        <thead>
                        <tr>
                            <th>Портфель</th>
                            <th>Общая стоимость</th>
                            <th>Доход</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            portfolios.map(portfolio => (
                                <PortfolioCard key={portfolio.id} portfolio={portfolio}
                                               deletePortfolio={deletePortfolio} updatePortfolio={updatePortfolio}/>
                            ))
                        }
                        <tr>
                            <td>
                                <TextInput
                                    onChange={event => setName(event.target.value)}
                                    variant={'unstyled'}
                                    value={name}
                                    icon={<IconBriefcase/>}/>
                            </td>
                            <td/>
                            <td/>
                            <td>
                                <IconCirclePlus className='interactiveIcon'
                                                color={'#32862d'}
                                                onClick={() => addPortfolio(name)}/>
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                </ScrollArea>
            </div>
        </div>
    )
}