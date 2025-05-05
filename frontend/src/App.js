import React, { useEffect, useState, useRef } from 'react';

const API_BASE = process.env.REACT_APP_API_BASE || 'http://localhost:8080';

function App() {
  const [meetings, setMeetings] = useState([]);
  const [logs, setLogs] = useState([]);
  const wsRef = useRef(null);

  useEffect(() => {
    fetch(`${API_BASE}/api/meetings`)
      .then(res => res.json())
      .then(data => setMeetings(data))
      .catch(err => console.error('Error fetching meetings:', err));
  }, []);

  const connectSocket = (employee) => {
    if (wsRef.current) {
      wsRef.current.close();
    }
    const wsUrl = `${API_BASE.replace('http', 'ws')}/ws?employee=${employee}`;
    const ws = new WebSocket(wsUrl);
    ws.onopen = () => setLogs(logs => [...logs, `Conectado a ${employee}`]);
    ws.onmessage = (msg) => setLogs(logs => [...logs, `[${employee}] ${msg.data}`]);
    ws.onclose = () => setLogs(logs => [...logs, `Desconectado de ${employee}`]);
    ws.onerror = (err) => setLogs(logs => [...logs, `Error WS: ${err}`]);
    wsRef.current = ws;
  };

  return (
    <div style={{ padding: '20px', fontFamily: 'Arial' }}>
      <h1>Meeting Manager Frontend</h1>
      <section>
        <h2>Reuniones</h2>
        {meetings.length === 0 ? (<p>Cargando...</p>) : (
          <ul>
            {meetings.map(m => (
              <li key={m.id}>{m.title} - {new Date(m.date).toLocaleString()}</li>
            ))}
          </ul>
        )}
      </section>
      <section>
        <h2>Sockets Empleados</h2>
        <div style={{ display: 'flex', gap: '10px' }}>
          {['Alice','Bob','Charlie','David','Eve'].map(emp => (
            <button key={emp} onClick={() => connectSocket(emp)}>{emp}</button>
          ))}
        </div>
        <div style={{ marginTop: '10px', maxHeight: '200px', overflowY: 'auto', border: '1px solid #ccc', padding: '10px' }}>
          {logs.map((l,i) => <div key={i}>{l}</div>)}
        </div>
      </section>
    </div>
  );
}

export default App;