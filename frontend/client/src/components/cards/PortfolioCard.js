import {ActionIcon, Textarea} from '@mantine/core';
import {useNavigate} from "react-router-dom";
import {IconBriefcase, IconCircleCheck, IconEditCircle, IconTrash} from "@tabler/icons"
import React, {forwardRef, useEffect, useRef, useState} from "react";
import "../../assets/styles/PortfolioCardStyles.css"

export function PortfolioCard({portfolio, deletePortfolio, updatePortfolio}) {
        const navigate = useNavigate();
        const [disabled, setDisabled] = useState(true);
        const [showConfirm, setShowConfirm] = useState(false);
        const [title, setTitle] = useState(portfolio.name);
        const inputRef = useRef(null);

        useEffect(() => {
            focus();
        }, [disabled])

        const focus = () => {
            inputRef.current.select();
        };

        const navigateToSecurityPage = () => {
            const value = disabled ? `/portfolio/${portfolio.id}/security` : "";
            navigate(value);
        }

        return (
            <div key={portfolio.id} className="portfolio">
                <div className={disabled ? "disabled-name" : "enabled-name"} onClick={navigateToSecurityPage}>
                    <Textarea
                        ref={inputRef}
                        value={title}
                        onChange={event => {
                            setTitle(event.target.value);
                            setShowConfirm(true)
                        }}
                        onFocus={event => disabled ? "" : event.target.select()}
                        variant={'unstyled'}
                        readOnly={disabled}
                        styles={{disabled: {cursor: 'pointer', color: '#00000F', opacity: 1}}}
                        icon={<IconBriefcase/>}
                        maxRows={1}
                        autosize={true}
                    />
                </div>
                <div className="totalCost">
                    {portfolio.totalCost} RUB
                </div>
                <div className="actions">
                    <div>
                        {showConfirm ?
                            <ActionIcon title="Принять">
                                <IconCircleCheck
                                    color={'#2aaf1d'}
                                    onClick={() => {
                                        updatePortfolio(portfolio, title);
                                        setShowConfirm(false);
                                        setDisabled(true);
                                    }}/>
                            </ActionIcon> :
                            <ActionIcon title="Переименовать">
                                <IconEditCircle
                                    color={'#2d5e86'}
                                    onClick={() => setDisabled(false)}/>
                            </ActionIcon>}
                    </div>
                    <div>
                        <ActionIcon title="Удалить">
                            <IconTrash color="black" onClick={() => deletePortfolio(portfolio.id)}/>
                        </ActionIcon>
                    </div>
                </div>
            </div>
        )
    }