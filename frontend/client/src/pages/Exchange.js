import React, {useEffect, useState} from "react";
import axios from "axios";
import {IconSearch} from "@tabler/icons";
import {Autocomplete, ScrollArea, Table, Button} from "@mantine/core";
import {SecurityShortInfoCard} from "../components/cards/SecurityShortInfoCard";
import {TabsHeader} from "../components/headers/TabsHeader";
import "../assets/styles/ExchangeStyles.css"

export function Exchange() {
    const [searchValue, setSearchValue] = useState('');
    const [securities, setSecurities] = useState([]);

    useEffect(() => {

    })

    const searchSecurity = () => {
        axios
            .get(`http://localhost:8080/exchange/${searchValue}`)
            .then(response => {
                setSecurities(response.data);
            })
    }

    return (
        <div className="exchange">
            <TabsHeader tab={"exchange"}/>
            <div className="searchSection">
                <Autocomplete
                    value={searchValue}
                    onChange={setSearchValue}
                    placeholder="Поиск"
                    icon={<IconSearch size={16} stroke={1.5}/>}
                    data={['Сбер', 'VTB', 'Рос']}
                />
                <Button component="button" onClick={searchSecurity}>
                    Поиск
                </Button>

            </div>
            <div className="searchResult">
                <ScrollArea>
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
                            securities.map(security => (
                                <SecurityShortInfoCard key={security.id} security={security}/>
                            ))
                        }
                        </tbody>
                    </Table>
                </ScrollArea>
            </div>
        </div>
    )
}