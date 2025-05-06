// src/App.js
import React, { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

function App() {
  const [meetings, setMeetings] = useState([]);
  const [title, setTitle]         = useState('');
  const [startTime, setStartTime] = useState('');
  const [duration, setDuration]   = useState(30);

  // Fetch from the real backend URL
  useEffect(() => {
    fetch('http://localhost:8080/api/meetings')
      .then(res => {
        if (!res.ok) throw new Error(res.statusText);
        return res.json();
      })
      .then(setMeetings)
      .catch(err => console.error('Error fetching meetings:', err));
  }, []);

  // Connect STOMP to the backend
  useEffect(() => {
    const socket = new SockJS('http://localhost:8080/ws');
    const client = new Client({
      webSocketFactory: () => socket,
      onConnect: () => {
        client.subscribe('/topic/meetings', msg => {
          const newMeeting = JSON.parse(msg.body);
          setMeetings(ms => [...ms, newMeeting]);
        });
      },
      onStompError: frame => console.error('STOMP error', frame),
    });
    client.activate();
    return () => client.deactivate();
  }, []);

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      const res = await fetch('http://localhost:8080/api/meetings', {
        method:  'POST',
        headers: { 'Content-Type': 'application/json' },
        body:    JSON.stringify({
          topic:           title,
          startTime,       // e.g. "2025-05-07T10:00:00Z"
          durationMinutes: duration,
        }),
      });
      if (!res.ok) throw new Error(await res.text());
      setTitle('');
      setStartTime('');
      setDuration(30);
    } catch (err) {
      console.error('Failed to create meeting:', err);
    }
  };

  return (
    <div style={{ padding: '1rem', fontFamily: 'Arial, sans-serif' }}>
      <h1>Meeting Manager</h1>

      <section>
        <h2>Crear nueva reunión</h2>
        <form onSubmit={handleSubmit} style={{ display: 'grid', gap: '0.5rem', maxWidth: '400px' }}>
          <input
            type="text"
            placeholder="Título de la reunión"
            value={title}
            onChange={e => setTitle(e.target.value)}
            required
          />
          <input
            type="datetime-local"
            value={startTime}
            onChange={e => setStartTime(e.target.value)}
            required
          />
          <input
            type="number"
            placeholder="Duración (minutos)"
            value={duration}
            onChange={e => setDuration(+e.target.value)}
            min={1}
            required
          />
          <button type="submit">Crear reunión</button>
        </form>
      </section>

      <section style={{ marginTop: '2rem' }}>
        <h2>Reuniones</h2>
        {meetings.length === 0 ? (
          <p>No hay reuniones aún.</p>
        ) : (
          <ul>
            {meetings.map(m => (
              <li key={m.id}>
                <strong>{m.topic || '(sin título)'}</strong> —{' '}
                {new Date(m.startTime).toLocaleString()} ({m.durationMinutes ?? '—'} min)
              </li>
            ))}
          </ul>
        )}
      </section>
    </div>
  );
}

export default App;
