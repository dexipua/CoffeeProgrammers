import {Link} from "react-router-dom";

const TeacherData = ({ teacher: { id, lastName, firstName } }) => {
    return (
        <Link to={`/teachers/getById/${id}`} style={{ textDecoration: 'none', color: 'inherit' }}>
                {lastName + " " + firstName}
        </Link>
    );
};

export default TeacherData;
