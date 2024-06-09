import * as React from 'react';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import TabContext from '@mui/lab/TabContext';
import TabList from '@mui/lab/TabList';
import TabPanel from '@mui/lab/TabPanel';
import StudentListForProfile from "../common/student/StudentListForProfile";
import MarkTableForSubject from "../common/mark/MarkTableForSubject";

export default function LabTabsForSubjectProfile({students, studentsWithMarks}) {
    const [value, setValue] = React.useState('1');

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    console.log(studentsWithMarks)
    return (
        <Box sx={{ width: '100%', typography: { body1: { fontFamily: 'Roboto' } } }}>
            <TabContext value={value}>
                <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                    <TabList onChange={handleChange} aria-label="lab API tabs example">
                        <Tab label="Students" value="1"/>
                        <Tab label="Markbook" value="2"/>
                    </TabList>
                </Box>
                <TabPanel value="1">
                    <StudentListForProfile students={students}/>
                </TabPanel>
                <TabPanel value="2">
                    <MarkTableForSubject students={studentsWithMarks}/>/>
                </TabPanel>
            </TabContext>
        </Box>
    );
}
