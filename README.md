﻿# 2023-12-otus-java-professional-antipov

Увеличение размера хипа до внесения изменений в код

| spend msec | sec | Xms   | Xmx  |
|------------|-----|-------|------|
| 13533      | 13  | 256   | 256  |
| 9104       | 9   | 512   | 512  |
| 8903       | 8   | 1024  | 1024 |
 | 8566       | 8   | 2048  | 2048 | 
 | 8536       | 8   | 4096  | 4096 |

После увелечения хипа до 1ГБ остальное увеличение не приводит к сокращению времени выполнения приложения.

Увелечение размера хипа после внесения изменений в код

| spend msec | sec | Xms   | Xmx  |
|------------|-----|-------|------|
| 2884       | 2   | 256   | 256  |
| 2946       | 2   | 512   | 512  |
| 3104       | 3   | 1024  | 1024 |
| 3281       | 3   | 2048  | 2048 | 
| 3226       | 3   | 4096  | 4096 |