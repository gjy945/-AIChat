<script setup lang="ts">
import { computed } from 'vue'
import { ElButton, ElEmpty, ElPopconfirm } from 'element-plus'
import { Plus, Delete } from '@element-plus/icons-vue'
import { useChatStore } from '../stores/chat'

const chatStore = useChatStore()

// 计算会话列表，按更新时间排序
const sortedConversations = computed(() => {
  return [...chatStore.conversations].sort((a, b) => b.updatedAt - a.updatedAt)
})

// 创建新会话
const createNewConversation = () => {
  chatStore.createConversation()
}

// 选择会话
const selectConversation = (id: string) => {
  chatStore.setActiveConversation(id)
}

// 删除会话
const deleteConversation = (id: string, event: Event) => {
  event.stopPropagation()
  chatStore.deleteConversation(id)
}

// 格式化日期
const formatDate = (timestamp: number) => {
  const date = new Date(timestamp)
  return date.toLocaleDateString()
}
</script>

<template>
  <div class="conversation-list">
    <div class="list-header">
      <h3>对话列表</h3>
      <el-button
        type="primary"
        :icon="Plus"
        circle
        size="small"
        @click="createNewConversation"
      />
    </div>
    
    <div class="list-content">
      <el-empty v-if="sortedConversations.length === 0" description="暂无对话" />
      
      <div
        v-for="conversation in sortedConversations"
        :key="conversation.id"
        class="conversation-item"
        :class="{ 'active': conversation.id === chatStore.activeConversationId }"
        @click="selectConversation(conversation.id)"
      >
        <div class="conversation-info">
          <div class="conversation-title">{{ conversation.title }}</div>
          <div class="conversation-date">{{ formatDate(conversation.updatedAt) }}</div>
        </div>
        
        <div class="conversation-actions" @click.stop>
          <el-popconfirm
            title="确定删除此对话吗？"
            @confirm="deleteConversation(conversation.id, $event)"
          >
            <template #reference>
              <el-button
                type="danger"
                :icon="Delete"
                circle
                size="small"
              />
            </template>
          </el-popconfirm>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.conversation-list {
  height: 100%;
  display: flex;
  flex-direction: column;
  border-right: 1px solid #e4e7ed;
}

.list-header {
  padding: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e4e7ed;
}

.list-header h3 {
  margin: 0;
  font-size: 16px;
  font-weight: 600;
}

.list-content {
  flex: 1;
  overflow-y: auto;
  padding: 8px;
}

.conversation-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px;
  border-radius: 8px;
  margin-bottom: 8px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.conversation-item:hover {
  background-color: #f5f7fa;
}

.conversation-item.active {
  background-color: #ecf5ff;
}

.conversation-info {
  flex: 1;
  overflow: hidden;
}

.conversation-title {
  font-size: 14px;
  font-weight: 500;
  margin-bottom: 4px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.conversation-date {
  font-size: 12px;
  color: #909399;
}

.conversation-actions {
  opacity: 0;
  transition: opacity 0.3s;
}

.conversation-item:hover .conversation-actions {
  opacity: 1;
}
</style>