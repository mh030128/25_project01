import { useState } from 'react'
import api from '../../api/axios';
import { Link, useNavigate } from 'react-router-dom';

import './LoginPage.css';

export default function LoginPage() {

  const [userId, setUserId] = useState('');
  const [userPw, setUserPw] = useState('');
  const [showPw, setShowPw] = useState(false);
  const [fieldErrors, setFieldErrors] = useState({});
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();     // 페이지 새로고침 막기

    // 이전 에러 지우기, 로딩 시작
    setError('');
    setFieldErrors({});
    setLoading(true);

    // 서버에 요청한 뒤 성공하면 저장 후 홈으로 이동
    try {
      const response = await api.post('/api/users/login', { userId, userPw });
      const data = response.data;

      localStorage.setItem('accessToken', data.accessToken);
      localStorage.setItem('userNo', data.userNo);
      localStorage.setItem('userId', data.userId);
      localStorage.setItem('userName', data.userName);

      navigate('/');
      // console.log('성공 응답: ', response.data)
    } catch (err) {                 // 실패 시 종류별로 에러 메시지 정리
      console.log('실패 응답 내용: ', err.response?.data)

      if (!err.response) {
        setError('서버에 연결할 수 없습니다.');
        return
      }

      const resData = err.response.data;

      if (typeof resData === 'string') {
        // 아이디, 비밀번호 틀렸을 때
        setError(resData);
      } else if (resData?.message) {
        setError(resData.message);
      } else if (resData && typeof resData === 'object') {
        // 아이디와 비밀번호 빈칸일 때
        setFieldErrors(resData);
      } else {
        setError('로그인에 실패했습니다.');
      }
    } finally {  // 성공 or 실패 시 로딩 끝
      setLoading(false)
    }
  }
  
  return (
    <div className='login-page'>
      <div className='login-box'>
        <h3 className='login-title'>로그인페이지</h3>
        <form onSubmit={handleSubmit}>
          <div className='form-group'>
            <input type='text' value={userId} 
                  placeholder='아이디를 입력해주세요.' aria-label='아이디'
                  className={fieldErrors.userId ? 'input-error' : ''}
                  onChange={(e) => setUserId(e.target.value)}/>
              {fieldErrors.userId && (<p className='field-error'>{fieldErrors.userId}</p>)}
          </div>
          <div className='form-group'>
            <div className='password-wrap'>
              <input type={showPw ? 'text' : 'password'} value={userPw} 
                    placeholder='비밀번호를 입력해주세요.' aria-label='비밀번호'
                    className={fieldErrors.userPw ? 'input-error' : ''}
                    onChange={(e) => setUserPw(e.target.value)}/>
              <button type='button' className='toggle-pw' onClick={() => setShowPw(!showPw)}>
                {showPw ? '숨기기' : '보기'}
              </button>
            </div>
            {fieldErrors.userPw && (
              <p className='field-error'>{fieldErrors.userPw}</p>
            )}
          </div>
          {error && <p className='form-error'>{error}</p>}

          <button type='submit' className='login-button' disabled={loading}>
            {loading ? '로그인 중' : '로그인'}
          </button>
        </form>
        <div className='login-links'>
          <Link to='/signup'>회원가입</Link>
        </div>
      </div>
    </div>
  )
}
