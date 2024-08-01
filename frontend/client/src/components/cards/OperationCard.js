import React from "react";
import {OperationTypeConverter} from "../../util/TypeConverter";
import "../../assets/styles/OperationStyles.css"

export function OperationCard({operation}) {
    return (
        <tr className="operation">
            <td>
                {operation.secid}
            </td>
            <td>
                {operation.name}
            </td>
            <td>
                {OperationTypeConverter(operation.operationType)}
            </td>
                <td>
                        {operation.quantity}
                </td>
            <td>
                {operation.operationCost}
            </td>
            <td>
                {operation.overall}
            </td>
            <td>
                {operation.profit}
            </td>
            <td>
                {operation.currency}
            </td>
            <td>
                {operation.operationDate}
            </td>
        </tr>
    )
}