import { useEffect, useState } from "react";
import api from "../../api/axios";
import { useNavigate } from "react-router-dom";

export default function MyPage() {

  const [me, setMe] = useState(null);
  const [error, setError] = useState('');
  const navigate = useNavigate();
  
  useEffect(() => {
    const token = localStorage.getItem('accessToken');
    if (!token) { 
      navigate('/login', { replace: true });
      return;
    }
    const fetchMe = async () => {
      try {
        const response = await api.get('/api/users/me');
        setMe(response.data);
      } catch (err) {
        if (err.response?.status === 401) {
          setError('로그인이 만료되었습니다. 다시 로그인해 주세요.');
        } else {
          setError('내 정보를 불러오지 못했습니다.');
        }
      }
    }
    fetchMe();
  }, [navigate]);

  if (error) return <p style={{ color: 'red' }}>{error}</p>
  if (!me) return <p>불러오는 중...</p>

  return (
    <div>
      <h3>MyPage</h3>
      <p>아이디: {me.userId}</p>
      <p>이름: {me.userName}</p>
      <p>이메일: {me.userEmail}</p>
      <p>전화번호: {me.userPhone}</p>
      <p>주소: ({me.userAddrPost}) {me.userAddr} {me.userAddrDetail}</p>
    </div>
  )
}
