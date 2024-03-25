# @formatter:off

$var_name='ti-tools_zookeeper-ui'
$var_version='1.0.0'

# exe repository 설정(for exe | msi 패키지)
class exe_repo {
  $releases='http://abis.ahnlab.com/artifactory/generic-local-repos-releases/com/ahnlab/asd/exe/'
  $snapshots='http://abis.ahnlab.com/artifactory/generic-local-repos-snapshots/com/ahnlab/asd/exe/'
}

# puppet 파일을 puppet master로 전송하기 위한 정보
class puppet {
  # puppet master host 정보
  $host='root@manager.asd.ahnlab.com'
  # puppet 파일을 전송할 puppet master path
  $path='/etc/puppetlabs/code/environments/mio/manifests/'
}

# integration test 정보 설정
class test {
  # test를 진행할 host정보
  $host='root@app01.asd.ahnlab.com'
  # integration 테스트를 위한 테스트 명시
  $app='${var_name}'
}

class deploy_env {
  $env='mio'
}

# yum repository 설정(for rpm 패키지)
class yum_repo {
  yumrepo { 'asd-releases':
    baseurl => 'http://abis.ahnlab.com/artifactory/yum-local-repos-releases'
  }
  yumrepo { 'asd-snapshots':
    baseurl => 'http://abis.ahnlab.com/artifactory/yum-local-repos-snapshots'
  }
}

node 'app01.asd.ahnlab.com' {
  include yum_repo

  rpm { 'asd-zkinit':
    version => '1.1.3.1',
    runservice => false,
    latest => true,
    actions_always => 'pre',
    pre_actions => []
  }

}

node 'app02.asd.ahnlab.com' {
  include yum_repo

  helm { '${var_name}':
    repo    => local,
    version => '${var_version}'
  }
}