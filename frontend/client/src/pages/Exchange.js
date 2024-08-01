import React, {useState} from "react";
import {IconSearch} from "@tabler/icons";
import {Autocomplete, Table} from "@mantine/core";
import {SecurityShortInfoCard} from "../components/cards/SecurityShortInfoCard";
import {TabsHeader} from "../components/headers/TabsHeader";
import "../assets/styles/ExchangeStyles.css"
import {useExchangeSearch} from "../api/service/ExchangeRequests";

export function Exchange() {
    const [query, setQuery] = useState('Рос');

    const {securities} = useExchangeSearch(query);

    return (
        <div className="exchange">
            <TabsHeader tab={"exchange"}/>
            <div className="searchSection">
                <Autocomplete
                    value={query}
                    onChange={setQuery}
                    placeholder="Поиск"
                    icon={<IconSearch size={16} stroke={1.5}/>}
                    data={['Сбер', 'VTB', 'Рос']}
                />


            </div>
            <div className="searchResult">
                    <Table highlightOnHover={true} sx={{minWidth: 800}} verticalSpacing="xs">
                        <thead>
                        <tr>
                            <th>Идентификатор</th>
                            <th>Название</th>
                            <th>Тип</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            Array.isArray(securities) ?
                            securities.map(security => (
                                <SecurityShortInfoCard key={security.id} security={security}/>
                            ))
                                : console.log(Array.isArray(securities))
                        }
                        </tbody>
                    </Table>
            </div>
        </div>
    )
}