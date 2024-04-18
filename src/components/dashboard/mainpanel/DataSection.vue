<script setup>
  import CustomButton from "../../common/CustomButton.vue";
  import SearchIcon from "../../../assets/searchIcon.svg";
  import useModalStore from "../../../stores/modal";
  import VueJsonPretty from "vue-json-pretty";
  import "vue-json-pretty/lib/styles.css";
  import { getClusterTree } from "../../../hooks/api/mainpanel/useApiGetClusterTree";
  import { useApiGetClusterTree } from "../../../hooks/api/mainpanel/useApiGetClusterTree";
  import { useApiRegisterNode } from "../../../hooks/api/mainpanel/useApiRegisterNode";
  import { useApiGetNodeMeta } from "../../../hooks/api/mainpanel/useApiGetNodeMeta";
  import { computed, ref, toRef, watch } from "vue";
  import CustomInput from "../../../components/common/CustomInput.vue";
  import { storeToRefs } from "pinia";
  import { api } from "../../../api/index";
  import useToastStore from "../../../stores/toast";
  import useGlobalState from "../../../stores/state";

  const props = defineProps({
    cluster: String,
    address: String,
  });

  const emits = defineEmits(["updateAddress"]);

  const store = useModalStore();
  const { modalInfo } = storeToRefs(store);
  const toastStore = useToastStore();
  const { openToast } = toastStore;
  const globalStore = useGlobalState();
  const { clusterTreeData, clusterMetaData } = storeToRefs(globalStore);

  const address = ref("/");
  const nodeAddress = ref("/");
  const nodeValue = ref("");

  const TgetClusterTree = useApiGetClusterTree(props.cluster, address.value);
  const getMetaData = useApiGetNodeMeta();
  const registerNode = useApiRegisterNode();

  /** 요청한 API의 데이터를 화면 상의 최신 데이터로 업데이트를 진행합니다. */
  const updateCluster = async (
    cluster = props.cluster,
    host = address.value,
  ) => {
    const { data: newData } = await TgetClusterTree(cluster, host);
    clusterTreeData.value = newData;
  };

  const getNodeMetaData = async (
    cluster = props.cluster,
    host = address.value,
    meta = true,
  ) => {
    const { data: newData } = await getMetaData(cluster, host, meta);
    clusterMetaData.value = newData;
  };

  /** 클러스터 이름이 변경되면서, 화면 상에 트리 구조를 보여주기 위해 API 호출 */
  const clusterName = computed((preValue) => {
    if (preValue !== props.cluster) {
      emits("updateAddress", "/");
      updateCluster();
      getNodeMetaData();
    }
    return props.cluster;
  });

  /** 클러스터 주소 변경 시에, 클러스터 데이터를 업데이트하고 메타데이터를 업데이트 */
  const clusterAddress = computed((preValue) => {
    if (preValue !== props.address) {
      updateCluster();
      getNodeMetaData();
    }

    return props.address;
  });

  /** 사용자가 주소를 입력하고, 엔터 키를 입력했을 때 세부 경로로 이동을 위해 API 호출 */
  const searchClusterTree = () => {
    emits("updateAddress", address.value);
  };

  /** 내보내기 이벤트 */
  const exportClusterToJSON = () => {
    const path = window.location.href;
    window.open(
      `${path}api/zkui/clusters/${clusterName.value}/export?path=${clusterAddress.value}`,
    );
  };

  /** 트리에 노드를 추가하고자 할 때 API 호출 */
  const handleRegisterNode = async () => {
    await registerNode.mutate({
      clusterName: clusterName.value,
      value: nodeValue.value,
      searchQuery: nodeAddress.value,
    });
  };

  /** 만약 노드 등록에 대한 TanStack 상태 값 */
  watch(registerNode.isPending, () => {
    if (registerNode.isSuccess.value) {
      getClusterTree(props.cluster)
        .then((res) => {
          openToast(true);
          clusterTreeData.value = toRef(res);
          nodeValue.value = "";
          nodeAddress.value = "/";
        })
        .catch(() => {
          openToast(false);
        });
    }

    if (registerNode.isError.value) {
      /** 에러가 발생하면, 현재의 주소에 따른 노드 값을 불러오고 모달에 표시를 해줍니다. */
      api
        .get(
          `/api/zkui/clusters/${clusterName.value}/node?path=${nodeAddress.value}`,
        )
        .then((res) => {
          modalInfo.value.clusterAddNodeAddress = nodeAddress.value;
          modalInfo.value.clusterAddNodeValue = nodeValue.value;
          modalInfo.value.clusterCurNodeValue = res.data.value;
          modalInfo.value.clusterName = clusterName.value;
          nodeValue.value = "";
          nodeAddress.value = "/";
        })
        .catch(() => {
          openToast(false);
        });
    }
  });

  const handleAddressChange = () => {
    if (event.target.value.length >= 0 && event.target.value[0] !== "/") {
      event.target.value = "/" + event.target.value;
    }
    if (event.target.name === "address") {
      address.value = event.target.value;
    }
  };
</script>

<template>
  <section :class="$style.section">
    <article :class="$style.article">
      <div :class="$style.title">{{ clusterName }}</div>
      <div :class="$style.utilSearch">
        <div :class="$style.searchBar">
          <SearchIcon :class="$style.icon" />
          <input
            name="address"
            placeholder="경로를 입력 후 검색 버튼을 눌러보세요"
            :value="clusterAddress"
            @keydown.enter="searchClusterTree"
            @input="handleAddressChange"
          />
        </div>
        <!--        <CustomButton-->
        <!--          @click="openModal('addNodeTree')"-->
        <!--          style="background: #3181f6"-->
        <!--          >노드 추가하기</CustomButton-->
        <!--        >-->
      </div>
      <div :class="$style.dataContainer">
        <div :class="$style.dataarea">
          <VueJsonPretty
            v-if="clusterTreeData"
            :data="clusterTreeData.value"
            treeJSONData="true"
            showSelectController
            showIcon
            showLineNumber
            :showDoubleQuotes="false"
            :class="$style.treeData"
          >
          </VueJsonPretty>
          <span v-else>데이터가 존재하지 않습니다</span>
        </div>
        <div :class="$style.sidebar">
          <div :class="$style.addClusterArea">
            <CustomInput
              v-model.trim="nodeAddress"
              :placeholder="'노드 경로를 입력해주세요'"
              :label="'노드 경로'"
              @input="handleAddressChange"
              :model-value="nodeAddress"
            />
            <CustomInput
              v-model.trim="nodeValue"
              :placeholder="'노드에 추가하고자 하는 값을 입력해주세요'"
              :label="'노드 값'"
            />
            <CustomButton
              @click="handleRegisterNode"
              :disabled="!(nodeAddress.length && nodeValue.length)"
              >노드 추가하기
            </CustomButton>
          </div>
          <div v-if="clusterMetaData" :class="$style.metaDataArea">
            <span> 메타 데이터 </span>
            <div :class="$style.metaData">
              <VueJsonPretty
                v-if="clusterMetaData"
                :data="clusterMetaData.value"
                treeJSONData="true"
                showSelectController
                :showDoubleQuotes="false"
                :class="$style.treeData"
              >
              </VueJsonPretty>
            </div>
          </div>
        </div>
      </div>
    </article>
    <div :class="$style.utilButton">
      <!--      <CustomButton>가져오기</CustomButton>-->
      <CustomButton @click="exportClusterToJSON">내보내기</CustomButton>
    </div>
  </section>
</template>

<style module>
  .section,
  .sidebar {
    display: flex;
    flex-direction: column;
    row-gap: 1rem;
    height: 100%;
  }

  .article {
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
    position: relative;
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
    height: 100%;
    min-width: 45rem;
    padding: 1rem;
    border-radius: 0.7rem;
    box-sizing: border-box;
  }

  .dataContainer {
    display: grid;
    grid-template-columns: 3.2fr 1fr;
    column-gap: 1rem;
  }

  .addClusterArea,
  .metaDataArea {
    background: #e5e8eb;
    width: 100%;
    height: 20rem;
    min-width: 24rem;
    padding: 1.3rem;
    border-radius: 0.7rem;
    box-sizing: border-box;

    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    row-gap: 1.5rem;
  }

  .metaDataArea {
    height: 100%;
    justify-content: normal;
    align-items: normal;
    color: #4e5968;
  }

  .metaData {
    background: white;
    border-radius: 0.7rem;
    height: 100%;
    padding: 1rem;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }

  .metaData > .treeData {
    height: 100%;
    display: flex;
    flex-direction: column;
    max-height: 25rem;
  }

  .treeData {
    height: 50rem;
    overflow-y: auto;
  }

  .treeData::-webkit-scrollbar {
    width: 8px;
    height: 8px;
    cursor: pointer;
  }

  .treeData::-webkit-scrollbar-thumb {
    height: 56px;
    border-radius: 8px;
    background-clip: content-box;
    background-color: rgba(0, 0, 0, 0.1);
  }
</style>
