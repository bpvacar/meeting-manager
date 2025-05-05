// frontend/src/App.js

import React, { useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

import './App.css';

const apiUrl = 'http://localhost:8080/api/meetings';

function App() {
  const [meetings, setMeetings] = useState([]);
  const [form, setForm] = useState({
    topic: '',
    location: '',
    startTime: '',
    endTime: ''
  });

  // 1) Carga inicial de reuniones y suscripción WS
  useEffect(() => {
    fetch(apiUrl)
      .then(res => res.json())
      .then(data => setMeetings(data))
      .catch(err => console.error('Error al cargar reuniones:', err));

    // Suscripción STOMP/SockJS
    const socket = new SockJS('http://localhost:8080/ws');
    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000
    });
    client.onConnect = () => {
      client.subscribe('/topic/meetings', msg => {
        const meeting = JSON.parse(msg.body);
        setMeetings(prev => [...prev, meeting]);
      });
    };
    client.activate();
    return () => client.deactivate();
  }, []);

  const handleChange = e => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async e => {
    e.preventDefault();

    // Convierte a ISO con Z
    const startISO = new Date(form.startTime).toISOString();
    const endISO   = new Date(form.endTime).toISOString();

    const payload = {
      topic:     form.topic,
      location:  form.location,
      startTime: startISO,
      endTime:   endISO
    };

    try {
      const response = await fetch(apiUrl, {
        method:  'POST',
        headers: { 'Content-Type': 'application/json' },
        body:    JSON.stringify(payload)
      });

      if (!response.ok) {
        console.error(
          'Error al crear reunión:',
          response.status,
          await response.text()
        );
        return;
      }

      // Parsea la reunión creada y la añade al estado
      const nueva = await response.json();
      // setMeetings(prev => [...prev, nueva]);

      // Resetea el formulario
      setForm({ topic: '', location: '', startTime: '', endTime: '' });
    } catch (err) {
      console.error('Fallo en request POST:', err);
    }
  };

  return (
    <div className="App p-4">
      <h1 className="text-2xl font-bold mb-4">Reuniones</h1>

      <form onSubmit={handleSubmit} className="mb-6 space-y-2">
        <input
          name="topic"
          placeholder="Tema"
          value={form.topic}
          onChange={handleChange}
          className="border p-2 w-full"
          required
        />
        <input
          name="location"
          placeholder="Ubicación"
          value={form.location}
          onChange={handleChange}
          className="border p-2 w-full"
          required
        />
        <input
          type="datetime-local"
          name="startTime"
          value={form.startTime}
          onChange={handleChange}
          className="border p-2 w-full"
          required
        />
        <input
          type="datetime-local"
          name="endTime"
          value={form.endTime}
          onChange={handleChange}
          className="border p-2 w-full"
          required
        />
        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded"
        >
          Crear reunión
        </button>
      </form>

      <ul>
        {meetings.map(m => (
          <li key={m.id} className="border-b py-2">
            <strong>{m.topic}</strong> en <em>{m.location}</em> de{' '}
            {new Date(m.startTime).toLocaleString()} a{' '}
            {new Date(m.endTime).toLocaleString()}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
