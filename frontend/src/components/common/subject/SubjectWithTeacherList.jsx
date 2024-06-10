import React from 'react';
import SubjectWithTeacherForList from "./SubjectWithTeacherForList";

const SubjectWithTeacherList = ({ subjects }) => {
    const containerStyle = {
        display: 'flex',
        flexWrap: 'wrap', // Allows items to wrap to the next line if necessary
        justifyContent: 'center', // Centers items horizontally
    };

    const itemStyle = {
        margin: '10px', // Adds spacing between items
    };

    return (
        <div style={containerStyle}>
            {subjects.map((subject, index) => (
                <div key={index} style={itemStyle}>
                    <SubjectWithTeacherForList
                        subjectResponse={subject}
                    />
                </div>
            ))}
        </div>
    );
};

export default SubjectWithTeacherList;
