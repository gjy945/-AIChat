import { defineStore } from 'pinia'
import { ref } from 'vue'
import { chatApi } from '../api/chat'

// 定义消息类型
export interface Message {
  id: string
  role: 'user' | 'assistant'
  content: string
  timestamp: number
  status?: 'sending' | 'success' | 'error'
}

export const useChatStore = defineStore('chat', () => {
  // 消息列表
  const messages = ref<Message[]>([])
  
  // 消息发送状态
  const loading = ref(false)
  
  // 发送消息
  const sendMessage = async (content: string, useStream: boolean = true) => {
    if (!content.trim()) return
    
    // 创建用户消息
    const userMessage: Message = {
      id: Date.now().toString(),
      role: 'user',
      content,
      timestamp: Date.now(),
      status: 'sending'
    }
    
    // 添加到消息列表
    messages.value.push(userMessage)
    
    try {
      // 设置加载状态为true
      loading.value = true
      
      // 更新用户消息状态
      userMessage.status = 'success'
      
      if (useStream) {
        // 创建AI回复消息（初始为空）
        const assistantMessage: Message = {
          id: Date.now().toString(),
          role: 'assistant',
          content: '',
          timestamp: Date.now(),
          status: 'sending'
        }
        
        // 添加到消息列表
        messages.value.push(assistantMessage)
        
        // 使用流式API
        await chatApi.sendMessageStream(
          content,
          // 处理每个数据块
          (chunk: string) => {
            assistantMessage.content += chunk
            // 确保在接收数据时状态保持为sending
            assistantMessage.status = 'sending'
          },
          // 完成回调
          () => {
            // 只有在完成回调时才更新状态为success并关闭loading
            assistantMessage.status = 'success'
            loading.value = false
          }
        )
      } else {
        // 使用普通API
        const response = await chatApi.sendMessage(content)
        
        // 添加AI回复
        const assistantMessage: Message = {
          id: Date.now().toString(),
          role: 'assistant',
          content: response || '抱歉，我无法理解您的问题。',
          timestamp: Date.now(),
          status: 'success'
        }
        
        messages.value.push(assistantMessage)
        loading.value = false
      }
    } catch (error) {
      console.error('发送消息失败:', error)
      userMessage.status = 'error'
      loading.value = false
    }
  }
  
  // 清空消息
  const clearMessages = () => {
    messages.value = []
  }
  
  return {
    messages,
    loading,
    sendMessage,
    clearMessages
  }
})