<script setup lang="ts">
import { computed, ref } from 'vue'
import { ElAvatar } from 'element-plus'
import type { Message } from '../stores/chat'
import MarkdownIt from 'markdown-it'

const props = defineProps<{
  message: Message
}>()

// 根据消息角色计算头像和样式
const avatarIcon = computed(() => {
  return props.message.role === 'assistant' ? 'https://cdn.jsdelivr.net/gh/twitter/twemoji@14.0.2/assets/svg/1f916.svg' : 'https://cdn.jsdelivr.net/gh/twitter/twemoji@14.0.2/assets/svg/1f464.svg'
})

// 计算消息容器的样式类
const messageClass = computed(() => {
  return {
    'message-container': true,
    'user-message': props.message.role === 'user',
    'assistant-message': props.message.role === 'assistant',
    'message-sending': props.message.status === 'sending',
    'message-error': props.message.status === 'error'
  }
})

// 格式化时间戳
const formattedTime = computed(() => {
  const date = new Date(props.message.timestamp)
  return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
})

// 创建markdown解析器实例
const md = new MarkdownIt()

// 处理消息内容，支持markdown和<think>标签
const processedContent = computed(() => {
  let content = props.message.content
  
  // 检查是否有<think>标签
  const thinkRegex = /<think>([\s\S]*?)<\/think>/g
  const hasThinkTags = thinkRegex.test(content)
  
  // 如果没有<think>标签，直接解析Markdown
  if (!hasThinkTags) {
    return md.render(content)
  }
  
  // 重置正则表达式状态
  thinkRegex.lastIndex = 0
  
  // 创建唯一的占位符，确保不会与内容冲突
  const placeholders = []
  const thinkContents = []
  
  // 替换所有<think>标签为占位符
  content = content.replace(thinkRegex, (match, thinkContent) => {
    // 使用更安全的占位符格式，避免与其他内容冲突
    const placeholder = `__THINK_PLACEHOLDER_${placeholders.length}_${Math.random().toString(36).substring(2, 10)}__`
    placeholders.push(placeholder)
    thinkContents.push(thinkContent)
    return placeholder
  })
  
  // 先解析Markdown
  let renderedContent = md.render(content)
  
  // 然后替换占位符为带样式的内容
  placeholders.forEach((placeholder, index) => {
    // 先解析think内容的Markdown
    const thinkHtml = md.render(thinkContents[index])
    
    // 使用正则表达式确保完整替换占位符
    const placeholderRegex = new RegExp(placeholder.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'), 'g')
    renderedContent = renderedContent.replace(
      placeholderRegex,
      `<div class="think-content">${thinkHtml}</div>`
    )
  })
  
  return renderedContent
})
</script>

<template>
  <div :class="messageClass">
    <div class="message-avatar">
      <el-avatar :size="40" :src="avatarIcon" />
    </div>
    <div class="message-content">
      <div class="message-header">
        <span class="message-role">{{ message.role === 'assistant' ? 'AI助手' : '我' }}</span>
        <span class="message-time">{{ formattedTime }}</span>
      </div>
      <div class="message-body">
        <div v-html="processedContent"></div>
      </div>
      <div v-if="message.status === 'sending'" class="message-status">
        <span>正在发送...</span>
      </div>
      <div v-else-if="message.status === 'error'" class="message-status error">
        <span>发送失败</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.message-container {
  display: flex;
  margin-bottom: 20px;
  padding: 0 20px;
}

.user-message {
  flex-direction: row-reverse;
}

.message-avatar {
  flex-shrink: 0;
  margin: 0 12px;
}

.message-content {
  max-width: 80%;
  border-radius: 8px;
  padding: 12px 16px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}

.user-message .message-content {
  background-color: #ecf5ff;
  border: 1px solid #d9ecff;
}

.assistant-message .message-content {
  background-color: #f5f7fa;
  border: 1px solid #e4e7ed;
}

.message-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 6px;
  font-size: 12px;
  color: #909399;
}

.message-body {
  font-size: 14px;
  line-height: 1.6;
  word-break: break-word;
}

.message-body :deep(.think-content) {
  color: #909399;
  font-weight: 300;
  font-style: italic;
  background-color: #f8f8f8;
  border-left: 3px solid #dcdfe6;
  padding: 8px 12px;
  margin: 8px 0;
  border-radius: 4px;
}

.message-body :deep(pre) {
  background-color: #f8f8f8;
  padding: 12px;
  border-radius: 4px;
  overflow-x: auto;
}

.message-body :deep(code) {
  background-color: #f0f0f0;
  padding: 2px 4px;
  border-radius: 3px;
  font-family: monospace;
}

.message-body :deep(ul), .message-body :deep(ol) {
  padding-left: 20px;
}

.message-body :deep(blockquote) {
  border-left: 4px solid #dfe2e5;
  padding-left: 16px;
  margin-left: 0;
  color: #666;
}

.message-status {
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
  text-align: right;
}

.message-status.error {
  color: #f56c6c;
}

.message-sending {
  opacity: 0.8;
}

.message-error .message-content {
  border-color: #fde2e2;
}
</style>