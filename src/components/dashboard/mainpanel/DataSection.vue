<script setup>
import CustomButton from "../../common/CustomButton.vue";
import getTree from "../../../data/getTree.json";
import SearchIcon from "../../../assets/searchIcon.svg";
import useModalStore from "../../../stores/modal";
import { useApiGetClusterTree } from "../../../hooks/api/sidepanel/useApiGetClusterTree";
import {computed, onMounted, onUpdated, ref, watch, watchEffect} from "vue";

const props = defineProps({
  cluster: String,
});

const store = useModalStore();
const { openModal } = store;
const address = ref("/");
const data = ref('');

const clusterName = computed(() => props.cluster);
const getClusterTree = useApiGetClusterTree(clusterName.value, address.value);
const SearchClusterTree = () => {
  data.value = getClusterTree.data;
}

watch(clusterName,  () => {
  console.log("클러스터 이름 : ", clusterName.value, "주소 :", address.value);
  const test = useApiGetClusterTree(clusterName, address.value);
  console.log(test.data, clusterName.value, address.value);
  data.value = "hi";
}, { immediate: true});

</script>

<template>
  <section :class="$style.section">
    <div :class="$style.title">{{ cluster }}</div>
    <div :class="$style.utilSearch">
      <div :class="$style.searchBar">
        <SearchIcon :class="$style.icon" />
        <input
          placeholder="경로를 입력 후 검색 버튼을 눌러보세요"
          :value="address"
          @keydown="(event) => {
            if (event.key === 'Enter') {
              SearchClusterTree();
            }
          }"
          @input="
            (event) => {
              if (
                event.target.value.length >= 0 &&
                event.target.value[0] !== '/'
              ) {
                event.target.value = '/' + event.target.value;
              }
              address = event.target.value;
            }
          "
        />
      </div>
      <CustomButton
        @click="openModal('addNodeTree')"
        style="background: #3181f6"
        >노드 추가하기</CustomButton
      >
    </div>
    <div :class="$style.dataarea">
      <xmp v-if="data">
        {{ data }}
      </xmp>

      <span v-else>데이터가 존재하지 않습니다</span>
    </div>
    <div :class="$style.utilButton">
      <CustomButton>가져오기</CustomButton>
      <CustomButton>내보내기</CustomButton>
    </div>
  </section>
</template>

<style module>
.section {
  display: flex;
  flex-direction: column;
  row-gap: 1rem;
}

.title {
  width: 100%;
  font-size: 28px;
  font-weight: 600;
  padding-bottom: 1rem;
  border: solid;

  border-left: 0;
  border-right: 0;
  border-top: 0;
}

.icon {
  position: absolute;
  width: 1.3rem;
  height: auto;
  margin-left: 1rem;
}

.searchBar {
  display: flex;
  align-items: center;
}

.utilSearch,
.utilButton {
  display: flex;
  height: 2.8rem;
}

.utilButton {
  margin-left: auto;
  column-gap: 0.9rem;
}

.searchBar > input {
  border: none;
  box-shadow: inset 0 0 0 1px rgba(0, 27, 55, 0.1);
  border-radius: 0.7rem;
  width: 22.5rem;
  min-height: 2.5rem;
  padding-left: 3rem;
  height: 100%;
}

.dataarea {
  background: #e5e8eb;
  width: 100%;
  height: 50rem;
  padding: 1rem;
  border-radius: 0.7rem;
  box-sizing: border-box;
  overflow-y: auto;
}
</style>
