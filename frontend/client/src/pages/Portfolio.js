import React, {useCallback, useEffect, useRef, useState} from "react";
import "../assets/styles/PortfoliosStyle.css"
import "../assets/styles/IconStyles.css"
import {ActionIcon, Table, TextInput} from "@mantine/core";
import {PortfolioCard} from "../components/cards/PortfolioCard";
import {IconBriefcase, IconCirclePlus} from "@tabler/icons"
import {
    PortfolioService
} from "../api/service/PortfolioRequests";

export function Portfolio() {
    const TEMPLATE = "Изменить имя"
    const [name, setName] = useState(TEMPLATE);
    const [page, setPage] = useState(0);
    const service
        = PortfolioService();

    useEffect(() => {
        const d = setTimeout(() => {
            service.getPortfolios(page);
        }, 500);
    }, [page])

    const observer = useRef();

    const lastPortfolioRef = useCallback(el => {
        if(observer.current) observer.current.disconnect();
        observer.current = new IntersectionObserver(entries => {
            if(entries[0].isIntersecting && service.hasMore) {
                setPage(prevState => prevState+1);
            }
        })
        if (el) observer.current.observe(el)
    }, [service.loading, service.hasMore]);


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
                                onFocus={event => event.target.select()}
                                variant={'unstyled'}
                                value={name}
                                icon={<IconBriefcase/>}/>
                        </td>
                        <td>
                            <ActionIcon onClick={() => {service.postPortfolio(name); setName(TEMPLATE)}}>
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
                        service.portfolios.map((portfolio, index) => {
                            if(service.portfolios.length-1 === index) {
                                return <PortfolioCard ref={lastPortfolioRef} key={portfolio.id} portfolio={portfolio}
                                                      deletePortfolio={service.deletePortfolio}
                                                      updatePortfolio={service.putPortfolio}/>
                            }
                            else {
                                return <PortfolioCard key={portfolio.id} portfolio={portfolio}
                                                      deletePortfolio={service.deletePortfolio}
                                                      updatePortfolio={service.putPortfolio}/>
                            }
                        })
                    }
                    <tr><td>{service.loading && 'Loading...'}</td></tr>
                    </tbody>
                </Table>
            </div>
        </div>
    )
}