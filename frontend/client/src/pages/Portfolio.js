import React, {useCallback, useEffect, useRef, useState} from "react";
import "../assets/styles/PortfoliosStyle.css"
import "../assets/styles/IconStyles.css"
import {ActionIcon, Table, TextInput} from "@mantine/core";
import {PortfolioCard} from "../components/cards/PortfolioCard";
import {IconBriefcase, IconCirclePlus} from "@tabler/icons"
import {
    deletePortfolio,
    getPortfolios, PortfolioService,
    postPortfolio,
    putPortfolio,
    useGetPortfolios
} from "../api/service/PortfolioRequests";
import ScrollPagination from "../components/navigation/ScrollPagination";

export function Portfolio() {
    const [portfolios, setPortfolios] = useState([]);
    const [name, setName] = useState("Новый портфель");
    const [page, setPage] = useState(0);
    const service
        = PortfolioService();
    const observer = useRef();

    const lastPortfolioRef = useCallback(el => {
        if(loading) return
        if(observer.current) observer.current.disconnect()
        observer.current = new IntersectionObserver(entries => {
            if(entries[0].isIntersecting) {
                console.log("visible");
            }
        })
        if (el) observer.current.observe(el)
    }, [loading]);

    useEffect(() => {
        service.request(page)
    }, [page])

    const addStatePortfolio = (name) => {
        postPortfolio(name)
            .then(newPortfolio => {
                //setPortfolios([...portfolios, newPortfolio]);
                portfolios.push(newPortfolio);
                setName("Новый портфель");
            });
    }

    const deleteStatePortfolio = (id) => {
        deletePortfolio(id)
            .then(() => {
                let clone = [...portfolios].filter(item => item.id !== id);
                setPortfolios(clone);
            });
    }

    const updateStatePortfolio = (id, oldName, newName, setName) => {
        putPortfolio(id, newName)
            .then(result => {
                let clone = [...portfolios].filter(item => item.id !== id);
                clone.push(result);
                setPortfolios(clone);
            })
            .catch(() => setName(oldName));
    }


    return (
        <div className="portfolioMain">
            <div className="portfolios">
                <Table className="table-header">
                    <thead>
                    <tr>
                        <th>Портфель</th>
                        <th>Общая стоимость</th>
                        <th/>
                        <th/>
                        <th/>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <TextInput
                                onChange={event => setName(event.target.value)}
                                variant={'unstyled'}
                                value={name}
                                icon={<IconBriefcase/>}/>
                        </td>
                        <td>
                            <ActionIcon onClick={() => addStatePortfolio(name)}>
                                <IconCirclePlus className='interactiveIcon'
                                                color={'#32862d'}/>
                            </ActionIcon>
                        </td>
                    </tr>
                    </tbody>
                </Table>
                <Table className="table-body">
                    <tbody>
                    {
                        portfolios.map((portfolio, index) => {
                            if(portfolios.length === index + 1) {
                                return <PortfolioCard ref={lastPortfolioRef} key={portfolio.id} portfolio={portfolio}
                                                      deletePortfolio={deleteStatePortfolio}
                                                      updatePortfolio={updateStatePortfolio}/>
                            }
                            else {
                                return <PortfolioCard key={portfolio.id} portfolio={portfolio}
                                                      deletePortfolio={deleteStatePortfolio}
                                                      updatePortfolio={updateStatePortfolio}/>
                            }
                        })
                    }
                    </tbody>
                </Table>
            </div>
        </div>
    )
}