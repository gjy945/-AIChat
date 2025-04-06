<script setup lang="ts">
import { ref } from 'vue'
import { ElButton, ElInput, ElSwitch } from 'element-plus'
import { useChatStore } from '../stores/chat'

const chatStore = useChatStore()
const inputMessage = ref('')
const inputRef = ref<InstanceType<typeof ElInput> | null>(null)
const useStreamOutput = ref(true)

// 发送消息
const sendMessage = async () => {
  if (!inputMessage.value.trim() || chatStore.loading) return
  
  await chatStore.sendMessage(inputMessage.value, useStreamOutput.value)
  inputMessage.value = ''
  
  // 自动聚焦输入框
  setTimeout(() => {
    inputRef.value?.focus()
  }, 100)
}

// 处理按键事件
const handleKeyDown = (evt: KeyboardEvent) => {
  // 按下Enter键发送消息，但Shift+Enter允许换行
  if (evt.key === 'Enter' && !evt.shiftKey) {
    evt.preventDefault()
    sendMessage()
  }
}
</script>

<template>
  <div class="chat-input-container">
    <el-input
      ref="inputRef"
      v-model="inputMessage"
      type="textarea"
      :rows="3"
      :placeholder="chatStore.loading ? '正在思考中...' : '输入消息，按Enter发送，Shift+Enter换行'"
      resize="none"
      :disabled="chatStore.loading"
      @keydown="handleKeyDown"
      autofocus
    />
    <div class="input-actions">
      <div class="stream-toggle">
        <span class="toggle-label">流式输出</span>
        <el-switch v-model="useStreamOutput" :disabled="chatStore.loading" />
      </div>
      <el-button
        type="primary"
        :disabled="!inputMessage.trim() || chatStore.loading"
        @click="sendMessage"
        :loading="chatStore.loading"
        round
      >
        {{ chatStore.loading ? '发送中...' : '发送' }}
      </el-button>
    </div>
  </div>
</template>

<style scoped>
.chat-input-container {
  padding: 16px;
  border-top: 1px solid #e4e7ed;
  background-color: #fff;
  position: relative;
}

.input-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
}

.stream-toggle {
  display: flex;
  align-items: center;
  gap: 8px;
}

.toggle-label {
  font-size: 14px;
  color: #606266;
}

:deep(.el-textarea__inner) {
  resize: none;
  border-radius: 8px;
  padding: 12px;
  font-size: 14px;
  line-height: 1.6;
}

:deep(.el-textarea__inner:focus) {
  border-color: #409eff;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}
</style>