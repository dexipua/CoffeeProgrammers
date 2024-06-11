import React from 'react';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';

const DynamicTabs = ({ tabs }) => {
    const [value, setValue] = React.useState('1');

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const tabList = tabs.map(({ label }, index) => (
        <Tab key={index} label={label} value={(index + 1).toString()} />
    ));

    const tabPanels = tabs.map(({ value }, index) => (
        <TabPanel key={index} value={(index + 1).toString()}>
            {value}
        </TabPanel>
    ));

    return (
        <Box sx={{ width: '100%', typography: { body1: { fontFamily: 'Roboto' } } }}>
            <TabContext value={value}>
                <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                    <TabList onChange={handleChange} aria-label="dynamic tabs">
                        {tabList}
                    </TabList>
                </Box>
                {tabPanels}
            </TabContext>
        </Box>
    );
};


export default DynamicTabs;

