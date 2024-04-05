<script setup>
import ZookeeperIcon from "../../../assets/zookeeperIcon.svg";
import ClusterItems from "./ClusterItems.vue";
import AhnLabIcon from "../../../assets/ahnlabIcon.svg";
import SmallText from "../../common/text/SmallText.vue";
import PlusIcon from "../../../assets/plusIcon.svg";
import useModalStore from "../../../stores/modal";
import useGlobalState from "../../../stores/state";
import { useApiGetCluster } from '../../../hooks/api/mainpanel/useApiGetCluster';
import {toRefs, watch} from "vue";
import {storeToRefs} from "pinia";

const store = useModalStore();
const { openModal } = store;
const state = useGlobalState();
const { updateClusterData } = state;
const { clusterData } = storeToRefs(state);
const props = defineProps({
  cluster: {
    type: String,
    default: "",
  },
});

const { data } = useApiGetCluster();
watch(data, () => {
  updateClusterData((Object.entries(data.value)));
}, )

const handleClick = () => {
  const tmp = {
    "host": "zk1.asd.ahnlab.com:2181,zk2.asd.ahnlab.com:2181",
    "name": "asd-app"
  }
  const dataSet = [tmp.name, tmp.host];
  clusterData.value.push(dataSet)
}
</script>

<template>
  <aside class="sidebar">
    <div class="sidebar-header">
      <ZookeeperIcon class="icon" width="0" height="0" />
      <div class="title">UI for Zookeeper</div>
    </div>
    <div class="sidebar-cluster-data">
      <!-- 반응형 데이터를 뿌려줘야함 -->
      <SmallText style="color: white"
        >현재 운용중인 클러스터 목록 ({{ clusterData.length }})</SmallText
      >
      <template v-if="clusterData.length">
        <ClusterItems
          v-for="item in clusterData"
          :key="item[1]"
          :name="item[0]"
          :class="item[0] === cluster && 'selected'"
          @click="$emit('selectCluster', item[0], item[1])"
        />
      </template>
      <PlusIcon
        @click="openModal('addCluster')"
        class="icon icon_p"
        width="0"
        height="0"
      />
    </div>
    <AhnLabIcon class="logo" />
  </aside>
</template>

<style scoped>
.icon_p {
  cursor: pointer;
  transition: 0.4s all;
  height: 2.3rem !important;
  width: fit-content;
  margin: 0 auto;
}

.sidebar {
  max-width: 10%;
  min-width: 350px;
  height: 100vh;
  background: #1c2536;
  border-top-right-radius: 1rem;
  border-bottom-right-radius: 1rem;
  padding: 1rem 0 1rem 0;
  box-sizing: border-box;

  display: flex;
  flex-direction: column;
  align-items: center;
}

.sidebar-header {
  display: flex;
  justify-content: center;
  align-items: center;
  column-gap: 1rem;
  width: 100%;

  border: solid 2px white;
  border-top: 0;
  border-left: 0;
  border-right: 0;
  padding-bottom: 0.8rem;
}

.sidebar-cluster-data {
  display: flex;
  flex-direction: column;
  row-gap: 1rem;
  width: 85%;
  height: 100%;
  padding-top: 1.2rem;
  padding-bottom: 1.2rem;
}

.title {
  font-size: 28px;
  font-weight: 700;
  color: white;
}

.icon {
  width: auto;
  height: 3rem;
}

.logo {
  transform: scale(25%);
}
</style>
