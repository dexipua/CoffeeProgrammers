import * as React from 'react';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';

export default function SubjectTabs({tab1, tab2}) {
    const [value, setValue] = React.useState('1');

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <>
            <TabContext value={value}>
                <Box sx={{borderBottom: 1, borderColor: 'divider'}}>
                    <TabList onChange={handleChange} aria-label="lab API tabs example">
                        <Tab label="Students" value="1"/>
                        <Tab label="Schedule" value="2"/>
                    </TabList>
                </Box>
                <TabPanel value="1">
                    {tab1}
                </TabPanel>
                <TabPanel value="2">
                    {tab2}
                </TabPanel>
            </TabContext>
        </>
    );
}