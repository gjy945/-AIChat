<script setup lang="ts">
import { ref, computed, onMounted, nextTick } from 'vue'
import { ElButton, ElEmpty } from 'element-plus'
import { Delete } from '@element-plus/icons-vue'
import { useChatStore } from '../stores/chat'
import ChatMessage from './ChatMessage.vue'
import ChatInput from './ChatInput.vue'

const chatStore = useChatStore()
const messagesContainer = ref<HTMLElement | null>(null)

// 消息列表
const messages = computed(() => {
  return chatStore.messages
})

// 清空消息
const clearMessages = () => {
  chatStore.clearMessages()
}

// 监听消息变化，自动滚动到底部
const scrollToBottom = async () => {
  await nextTick()
  if (messagesContainer.value) {
    messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
  }
}

// 组件挂载后滚动到底部
onMounted(() => {
  scrollToBottom()
})
</script>

<template>
  <div class="chat-container" style="grid-template-columns: 1fr;">
    <!-- 主聊天区域 -->
    <div class="main-content">
      <!-- 聊天头部 -->
      <div class="chat-header">
        <h2>AI聊天助手</h2>
        <div class="header-actions">
          <el-button
            type="danger"
            :icon="Delete"
            circle
            size="small"
            @click="clearMessages"
            title="清空消息"
          />
        </div>
      </div>
      
      <!-- 消息区域 -->
      <div ref="messagesContainer" class="messages-container">
        <template v-if="messages.length > 0">
          <ChatMessage
            v-for="message in messages"
            :key="message.id"
            :message="message"
          />
        </template>
        <el-empty v-else description="开始一个新的对话吧！" />
      </div>
      
      <!-- 输入区域 -->
      <div class="input-container">
        <ChatInput />
      </div>
    </div>
  </div>
</template>

<style scoped>
.chat-container {
  display: flex;
  height: 100vh;
  width: 100%;
  overflow: hidden;
}

.sidebar {
  width: 280px;
  height: 100%;
  background-color: #fff;
  border-right: 1px solid #e4e7ed;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 100%;
  background-color: #f5f7fa;
}

.chat-header {
  padding: 16px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #fff;
  border-bottom: 1px solid #e4e7ed;
}

.chat-header h2 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}

.header-actions {
  display: flex;
  gap: 8px;
}

.messages-container {
  flex: 1;
  overflow-y: auto;
  padding: 20px 0;
  display: flex;
  flex-direction: column;
}

.input-container {
  background-color: #fff;
}

/* 自定义滚动条 */
.messages-container::-webkit-scrollbar {
  width: 6px;
}

.messages-container::-webkit-scrollbar-thumb {
  background-color: #c0c4cc;
  border-radius: 3px;
}

.messages-container::-webkit-scrollbar-track {
  background-color: #f5f7fa;
}
</style>