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

## 📐 Fastlane setup
    https://proandroiddev.com/ci-cd-pipeline-for-flavoured-android-apps-using-fastlane-and-github-actions-51667b7175af
