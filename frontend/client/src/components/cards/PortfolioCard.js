import {TextInput} from '@mantine/core';
import {useNavigate} from "react-router-dom";
import {IconBriefcase, IconCircleCheck, IconEditCircle, IconTrash} from "@tabler/icons"
import React, {useState} from "react";
import "../../assets/styles/IconStyles.css"

export function PortfolioCard({portfolio, deletePortfolio, updatePortfolio}) {
    const navigate = useNavigate();
    const [disabled, setDisabled] = useState(true);
    const [showConfirm, setShowConfirm] = useState(false);
    const [name, setName] = useState(portfolio.name);

    const navigateToSecurityPage = () => {
        const value = disabled ? `/portfolio/${portfolio.id}/security` : "";
        navigate(value);
    }

    return (<tr key={portfolio.id}>
            <td onClick={navigateToSecurityPage}>
                <TextInput
                    value={name}
                    onChange={event => {setName(event.target.value); setShowConfirm(true)}}
                    variant={'unstyled'}
                    disabled={disabled}
                    styles={{disabled: {cursor: 'pointer', color: '#00000F', opacity: 1}}}
                    icon={<IconBriefcase/>}
                />
            </td>
            <td>{portfolio.totalCost}</td>
            <td>
                {portfolio.profit}
            </td>
            <td>
                {showConfirm ? <IconCircleCheck
                    className='interactiveIcon'
                    color={'#2aaf1d'}
                    onClick={() => {
                        updatePortfolio(portfolio.id, portfolio.name, name, setName);
                        setShowConfirm(false);
                        setDisabled(true);
                    }}/> : <IconEditCircle
                    className='interactiveIcon'
                    color={'#2d5e86'}
                    onClick={() => setDisabled(false)}/>}
            </td>
            <td><IconTrash className='interactiveIcon' onClick={() => deletePortfolio(portfolio.id)}/></td>
        </tr>)
}