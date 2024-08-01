import React from "react";
import {Anchor} from "@mantine/core";
import {TypeConverterForSecurity} from "../../util/TypeConverter";
import {useNavigate} from "react-router-dom";

export function SecurityShortInfoCard({security}) {
    const navigate = useNavigate();

    const showFullInfo = () => {
        navigate(`${security.exchangeName}/security/${security.secid}`);
    }

    return (
        <tr onClick={showFullInfo}>
            <td>
                {security.secid}
            </td>
            <td>
                <Anchor>
                    {security.shortname}
                </Anchor>
            </td>
            <td>{TypeConverterForSecurity(security.group)}</td>
        </tr>
    )
}