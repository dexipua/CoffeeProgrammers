import React from 'react';
import SubjectSimpleView from "./SubjectSimpleView";
import {Grid} from "@mui/material";

const SubjectWithTeacherList = ({ subjects }) => {

    return (
        <Grid  justifyContent="center">
            {subjects.map((subject) => (
                <Grid item key={subject.id}>
                    <SubjectSimpleView subject={subject} />
                </Grid>
            ))}
        </Grid>

    );
};

export default SubjectWithTeacherList;
