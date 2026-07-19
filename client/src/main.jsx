import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'

import 'bootstrap/dist/css/bootstrap.css'; //referencing to the bootstrap
import 'bootstrap/dist/js/bootstrap.bundle.js';//referencing the js file
import 'bootstrap-icons/font/bootstrap-icons.css';
import {BrowserRouter} from "react-router-dom";
import {AppContextProvider} from "./context/AppContext.jsx"; //referencing bootstrap icons


createRoot(document.getElementById('root')).render(
  <BrowserRouter>
    <AppContextProvider>
        <App />
    </AppContextProvider>
  </BrowserRouter>

)
