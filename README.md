## 📐 Architecture Diagram

```
        ┌─────────────┐
        │ Composable  │
        │ (UI Layer)  │◄───── Collects ─────┐
        └────┬────────┘                     │
             │                              │
             ▼                              │
        ┌───────────────┐       Emits       │
        │  ViewModel    │─────────────────► SideEffect (e.g., NavigateToDetail)
        │  (MVI entry)  │                   │
        └────┬──────────┘                   │
             │ dispatches                   ▼
             ▼                         ┌─────────────┐
        ┌───────────────┐              │NavController│
        │   Delegate    │              └─────────────┘
        │ (MVI Engine)  │
        └────┬──────┬───┘
             │      │
             │      │
             ▼      ▼
        ┌────────┐ ┌────────────────────────────┐
        │Reducer │ │        Middleware          │
        │(Sync)  │ │(Async: API, Storage, etc.) │
        └────────┘ └────────────────────────────┘
```

## 📐 Cors bypass
    https://chromewebstore.google.com/detail/moesif-origincors-changer/
