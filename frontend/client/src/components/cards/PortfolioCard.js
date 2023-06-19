import {ActionIcon, TextInput} from '@mantine/core';
import {useNavigate} from "react-router-dom";
import {IconBriefcase, IconCircleCheck, IconEditCircle, IconTrash} from "@tabler/icons"
import React, {forwardRef, useEffect, useRef, useState} from "react";
import "../../assets/styles/IconStyles.css"

export const PortfolioCard =
    forwardRef(function PortfolioCard({portfolio, deletePortfolio, updatePortfolio}, ref) {
        const navigate = useNavigate();
        const [disabled, setDisabled] = useState(true);
        const [showConfirm, setShowConfirm] = useState(false);
        const [name, setName] = useState(portfolio.name);
        const inputRef = useRef(null);

        useEffect(() => {
            focus();
        }, [disabled])

        const focus = () => {
            inputRef.current.focus();
        };


        const navigateToSecurityPage = () => {
            const value = disabled ? `/portfolio/${portfolio.id}/security` : "";
            navigate(value);
        }


        return (
            <tr ref={ref} key={portfolio.id}>
                <td onClick={navigateToSecurityPage}>
                    <TextInput
                        ref={inputRef}
                        value={name}
                        onChange={event => {
                            setName(event.target.value);
                            setShowConfirm(true)
                        }}
                        onFocus={event => event.target.select()}
                        variant={'unstyled'}
                        disabled={disabled}
                        styles={{disabled: {cursor: 'pointer', color: '#00000F', opacity: 1}}}
                        icon={<IconBriefcase/>}
                    />
                </td>
                <td>{portfolio.totalCost}</td>
                <td>
                    <ActionIcon>
                        {showConfirm ? <IconCircleCheck
                            color={'#2aaf1d'}
                            onClick={() => {
                                updatePortfolio(portfolio, name);
                                setShowConfirm(false);
                                setDisabled(true);
                            }}/> : <IconEditCircle
                            color={'#2d5e86'}
                            onClick={() => setDisabled(false)}/>}
                    </ActionIcon>
                </td>
                <td>
                    <ActionIcon>
                        <IconTrash color="black" onClick={() => deletePortfolio(portfolio.id)}/>
                    </ActionIcon>
                </td>
            </tr>
        )
    });