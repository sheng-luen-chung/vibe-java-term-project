# Week 01 Chat Summary and Week 02 Plan

## 本次對話完成了什麼（摘要）

### 1. Week 0 初始化與 GitHub 流程建立
- 建立本機 repo 與 `main` 分支。
- 建立基礎檔案：
  - `README.md`
  - `.gitignore`
  - `.github/pull_request_template.md`
  - `docs/WEEKLY_PR_TEMPLATE.md`
- 完成 Week 0 branch、push、PR（Repo 與 PR 流程已打通）。

### 2. Week 1 三個 prompt 的程式實作
- Prompt 1：
  - 建立 Java Swing 單檔視窗（800x600、黑底）。
  - 紅色圓球（半徑 20）跟隨滑鼠。
- Prompt 2：
  - 紅球改成慣性平滑跟隨（延遲感）。
  - 背景加入 50 顆白色星點向下移動（星際飛行感）。
- Prompt 3：
  - 滑鼠按下觸發 Boost：球變黃、放大 2 倍、0.2 秒後恢復。
  - 主控台輸出：`Engine Boost!`

### 3. 課程相容性調整（Java SE 7）
- 將 Java 8+ 語法改為 Java SE 7 風格：
  - 移除 lambda 與 method reference。
  - 改用 `new Runnable(){...}`、`new ActionListener(){...}`。
- 說明 Swing 的 EDT 概念與 `SwingUtilities.invokeLater(...)` 用途。

### 4. 週報與展示證據工具
- 建立/更新週報與交接檔：
  - `docs/weekly/week01.md`
  - `docs/weekly/week01-handoff.md`
- 建立展示證據腳本：
  - `scripts/evidence/capture-screenshot.ps1`
  - `scripts/evidence/import-latest-recording.ps1`
  - `scripts/evidence/run-week1-evidence.ps1`
- 產出證據檔：
  - `docs/weekly/assets/week01-run.png`
  - `docs/weekly/assets/week01-demo.mp4`

### 5. 依作業規範完成 Git 交付
- branch 命名改為規範格式：`w01-swing-mouse-inertia`
- 有意義 commit（`feat:`、`docs:`）與 push 已完成。
- 建立 Week 1 PR：`W01 Swing 視窗與滑鼠慣性追蹤`

---

## Week 2 要怎麼進行（建議路線）

## 本週主題（建議）
- `W02 鍵盤控制與玩家飛船雛形`

## 先做的 5 件事（對齊規範）
1. 開新分支：`w02-keyboard-ship`
2. 完成至少一個有意義 `feat` commit（建議 2 個：功能 + 文件）
3. 更新 `docs/weekly/week02.md`
4. 準備展示證據（截圖或短影片）
5. 開 PR 到 `main`（標題：`W02 鍵盤控制與玩家飛船雛形`）

## Week 2 實作目標（技術）
1. 把紅球改成「玩家物件」概念（Player）。
2. 加入鍵盤控制（左右或 WASD）。
3. 加入邊界判斷（不能飛出畫面）。
4. 保留星空背景與更新迴圈。
5. 程式維持 Java SE 7 寫法。

## 建議 prompt（直接可貼給 AI）
1. `請把目前的紅球改成可用鍵盤左右移動的玩家飛船，速度固定，不能超出視窗邊界，保持 Java SE 7 寫法。`
2. `請把輸入處理與更新邏輯整理成較清楚的方法，例如 updatePlayer、handleInput，但先維持單檔。`
3. `請加入一個最小的 HUD（例如座標或速度文字），方便展示控制效果。`

## 建議 commit 訊息
- `feat: add keyboard movement and boundary clamp for player`
- `refactor: separate input and player update methods`
- `docs: add week02 progress report and evidence`

## 建議驗收清單（Week 2）
- 可用鍵盤穩定控制玩家移動。
- 玩家不會超出視窗左右邊界。
- 星點背景仍持續運作。
- `javac` 編譯成功、執行無錯誤。
- `docs/weekly/week02.md` 與證據檔完整。

## Week 2 執行順序（最短流程）
1. 先做功能（鍵盤移動 + 邊界）
2. 再補文件（week02.md）
3. 錄製證據（截圖 + 10–30 秒短影片）
4. commit / push / PR
