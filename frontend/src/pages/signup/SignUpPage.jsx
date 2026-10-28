import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../../api/axios';

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

  const [pwConfirm, setPwConfirm] = useState('');
  const [fieldErrors, setFieldErrors] = useState({});
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const formatPhone = (value) => {
  const digits = value.replace(/\D/g, '').slice(0, 11);

  if (digits.length <= 3) return digits;
  if (digits.length <= 6) return `${digits.slice(0, 3)}-${digits.slice(3)}`;
  if (digits.length <= 10) return `${digits.slice(0, 3)}-${digits.slice(3, 6)}-${digits.slice(6)}`;
  return `${digits.slice(0, 3)}-${digits.slice(3, 7)}-${digits.slice(7)}`;
  }

  const handleChange = (e) => {
    const { name, value } = e.target;
    const newValue = name === 'userPhone' ? formatPhone(value) : value;
    setForm({ ...form, [name]: newValue });

    // 입력 중인 칸에 에러가 있으면 그 칸만 지우기
    if (fieldErrors[name]) {
      setFieldErrors({ ...fieldErrors, [name]: '' });
    }
  }

  // 비밀번호 확인 칸 전용
  const handlePwConfirmChange = (e) => {
    setPwConfirm(e.target.value);

    if (fieldErrors.pwConfirm) {
      setFieldErrors({ ...fieldErrors, pwConfirm: '' });
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setFieldErrors({});

    // 비밀번호 확인은 서버에 보내기 전에 프론트에서 검사
    if (form.userPw !== pwConfirm) {
      setFieldErrors({ pwConfirm: '비밀번호가 일치하지 않습니다.' });
      return;
    }

    setLoading(true);

    try {
      await api.post('/api/users/signup', form);
      alert('회원가입이 완료되었습니다. 로그인해 주세요.');
      navigate('/login');
    } catch (err) {
      if (!err.response) {
        setError('서버에 연결할 수 없습니다.');
        return;
      }

      const resData = err.response.data;

      if (typeof resData === 'string') {
        // 중복 아이디, 이메일, 전화번호
        setError(resData);
      } else if (resData?.message) {
        setError(resData.message);
      } else if (resData && typeof resData === 'object') {
        // 빈칸, 형식 오류
        setFieldErrors(resData);
      } else {
        setError('회원가입에 실패했습니다.');
      }
    } finally {
      setLoading(false);
    }
  }

  return (
    <div>
      <h3>회원가입 페이지</h3>

      <form onSubmit={handleSubmit} noValidate>
        <div className="form-group">
          <input
            type="text"
            name="userId"
            value={form.userId}
            onChange={handleChange}
            placeholder="아이디 (영문, 숫자, _ 사용 6~30자)"
            className={fieldErrors.userId ? 'input-error' : ''}
          />
          {fieldErrors.userId && <p className="field-error">{fieldErrors.userId}</p>}
        </div>

        <div className="form-group">
          <input
            type="text"
            name="userName"
            value={form.userName}
            onChange={handleChange}
            placeholder="이름"
            className={fieldErrors.userName ? 'input-error' : ''}
          />
          {fieldErrors.userName && <p className="field-error">{fieldErrors.userName}</p>}
        </div>

        <div className="form-group">
          <input
            type="password"
            name="userPw"
            value={form.userPw}
            onChange={handleChange}
            placeholder="비밀번호 (8~20자)"
            className={fieldErrors.userPw ? 'input-error' : ''}
          />
          {fieldErrors.userPw && <p className="field-error">{fieldErrors.userPw}</p>}
        </div>

        <div className="form-group">
          <input
            type="password"
            value={pwConfirm}
            onChange={handlePwConfirmChange}
            placeholder="비밀번호 확인"
            className={fieldErrors.pwConfirm ? 'input-error' : ''}
          />
          {fieldErrors.pwConfirm && <p className="field-error">{fieldErrors.pwConfirm}</p>}
        </div>

        <div className="form-group">
          <input
            type="email"
            name="userEmail"
            value={form.userEmail}
            onChange={handleChange}
            placeholder="이메일 (example@email.com)"
            className={fieldErrors.userEmail ? 'input-error' : ''}
          />
          {fieldErrors.userEmail && <p className="field-error">{fieldErrors.userEmail}</p>}
        </div>

        <div className="form-group">
          <input
            type="tel"
            name="userPhone"
            value={form.userPhone}
            onChange={handleChange}
            placeholder="휴대폰 번호 (010-1234-5678)"
            className={fieldErrors.userPhone ? 'input-error' : ''}
          />
          {fieldErrors.userPhone && <p className="field-error">{fieldErrors.userPhone}</p>}
        </div>

        <div className="form-group">
          <input
            type="text"
            name="userAddrPost"
            value={form.userAddrPost}
            onChange={handleChange}
            placeholder="우편번호"
            className={fieldErrors.userAddrPost ? 'input-error' : ''}
          />
          {fieldErrors.userAddrPost && <p className="field-error">{fieldErrors.userAddrPost}</p>}
        </div>

        <div className="form-group">
          <input
            type="text"
            name="userAddr"
            value={form.userAddr}
            onChange={handleChange}
            placeholder="주소"
            className={fieldErrors.userAddr ? 'input-error' : ''}
          />
          {fieldErrors.userAddr && <p className="field-error">{fieldErrors.userAddr}</p>}
        </div>

        <div className="form-group">
          <input
            type="text"
            name="userAddrDetail"
            value={form.userAddrDetail}
            onChange={handleChange}
            placeholder="상세주소"
            className={fieldErrors.userAddrDetail ? 'input-error' : ''}
          />
          {fieldErrors.userAddrDetail && (
            <p className="field-error">{fieldErrors.userAddrDetail}</p>
          )}
        </div>

        {error && <p className="form-error">{error}</p>}

        <button type="submit" disabled={loading}>
          {loading ? '가입 중...' : '회원가입'}
        </button>
      </form>
    </div>
  )
}