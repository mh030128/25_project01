import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api/axios";

export default function SignUpPage() {

  const [form, setForm] = useState({
    userId: '',
    userName: '',
    userPw: '',
    userEmail: '',
    userPhone: '',
    userAddrPost: '',
    userAddr: '',
    userAddrDetail: '',
  });

  const navigate = useNavigate();

  const [pwConfirm, setPwConfirm] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  }

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await api.post('/api/users/signup', form);
      alert ('회원가입이 완료되었습니다. 로그인 해 주세요.');
      navigate('/login')
    } catch (err) {
      console.log('오류 종류:', err.code, err.message);
      console.log('실패 상태 코드:', err.response?.status);
      console.log('실패 응답:', err.response?.data);
    }
    // console.log('보낼 데이터:', form);
    // console.log('비밀번호 확인:', pwConfirm);
  }

  return (
    <div>
      <h3>회원가입 페이지</h3>
      
      <form onSubmit={handleSubmit}>
        <div>
          <input type='text' 
                 name="userId" value={form.userId} 
                 onChange={handleChange} 
                 placeholder="아이디 (영문, 숫자, _ 사용 6~30자)" 
          />
        </div>
        <div>
          <input type="text"
                 name="userName" value={form.userName}
                 onChange={handleChange} 
                 placeholder="이름" />
        </div>
        <div>
          <input type="password"
                 name="userPw" value={form.userPw}
                 onChange={handleChange}
                 placeholder="비밀번호" />
        </div>
        <div>
          <input type="password" 
                 value={pwConfirm}
                 onChange={(e) => setPwConfirm(e.target.value)} 
                 placeholder="비밀번호 확인" />
        </div>
        <div>
          <input type="email"
                 name="userEmail" value={form.userEmail}
                 onChange={handleChange}
                 placeholder="이메일 (example@email.com)" />
        </div>
        <div>
          <input type="tel"
                 name="userPhone" value={form.userPhone}
                 onChange={handleChange}
                 placeholder="휴대폰 번호 (010-1234-5678)" />
        </div>
        <div>
          <input type="text"
                 name="userAddrPost" value={form.userAddrPost}
                 onChange={handleChange} 
                 placeholder="우편번호" />
        </div>
        <div>
          <input type="text" 
                 name="userAddr" value={form.userAddr}
                 onChange={handleChange}
                 placeholder="주소" />
        </div>
        <div>
          <input type="text" 
                 name="userAddrDetail" value={form.userAddrDetail}
                 onChange={handleChange}
                 placeholder="상세주소" />
        </div>

        <button type="submit">회원가입</button>
      </form>
    </div>
  )
}
