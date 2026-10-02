import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";
import "./LoginPage.css";

function LoginPage() {
  const [userId, setUserId] = useState("");
  const [userPw, setUserPw] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const response = await api.post("/api/users/login", { userId, userPw });
      const { accessToken, userNo, userId: loggedInUserId, userName } = response.data;

      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("userNo", userNo);
      localStorage.setItem("userId", loggedInUserId);
      localStorage.setItem("userName", userName);

      navigate("/");
    } catch (err) {
      if (err.response) {
        setError(err.response.data?.message || err.response.data || "로그인에 실패했습니다.");
      } else {
        setError("서버에 연결할 수 없습니다.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h1>로그인</h1>

        <div className="form-group">
          <label htmlFor="userId">아이디</label>
          <input
            id="userId"
            type="text"
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="userPw">비밀번호</label>
          <input
            id="userPw"
            type="password"
            value={userPw}
            onChange={(e) => setUserPw(e.target.value)}
            required
          />
        </div>

        {error && <p className="error-message">{error}</p>}

        <button type="submit" disabled={loading}>
          {loading ? "로그인 중..." : "로그인"}
        </button>
      </form>
    </div>
  );
}

export default LoginPage;