import {Link} from "react-router-dom";

const SubjectData = ({ subject: { id, name } }) => {
    return (
        <Link to={`/subjects/getById/${id}`}
              style={{ textDecoration: 'none', color: 'inherit' }}>
            <p>{name}</p>
        </Link>
    );
};

export default SubjectData;
