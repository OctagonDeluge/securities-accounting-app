import React from "react";
import {LineChart, CartesianGrid, XAxis, YAxis, Tooltip, Legend, Line} from "recharts";

export function PriceChart({data, namings}) {

    return (
        <LineChart width={850} height={300} data={data}
            margin={{
                top: 5,
                right: 30,
                left: 20,
                bottom: 5,
            }}
        >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey={namings.x}/>
            <YAxis dataKey={namings.y}/>
            <Tooltip />
            <Legend />
            <Line type="monotone" dataKey={namings.line} stroke="#8884d8" name="Цена" dot={false} />
        </LineChart>
    )
}