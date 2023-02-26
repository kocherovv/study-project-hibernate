package org.example.service;

/*
@ExtendWith(MockitoExtension.class)
public class LabelServiceTest {
    private LabelDtoMapper labelDtoMapper;
    private LabelMapper labelMapper;
    private PostDtoMapper postDtoMapper;
    private PostMapper postMapper;
    @Mock
    private LabelRepositoryImpl labelRepository;
    @Mock
    private PostRepositoryImpl postRepository;
    private LabelService labelService;

    @BeforeEach
    void init() {
        labelDtoMapper = new LabelDtoMapper();
        labelMapper = new LabelMapper();
        postDtoMapper = new PostDtoMapper(labelDtoMapper);
        postMapper = new PostMapper(labelMapper);

        labelDtoMapper.setPostDtoMapper(postDtoMapper);
        labelMapper.setPostMapper(postMapper);

        labelService = new LabelService(
            labelRepository, postRepository, labelMapper, labelDtoMapper, postDtoMapper);
    }

    @Test
    void testIFindById_Found() {
        var expectedLabel = new Label(10, "test", new ArrayList<>());
        var expectedPosts = new ArrayList<Post>();

        expectedPosts.add(new Post(
            1, 1, LocalDateTime.now(), LocalDateTime.now(),
            "123", PostStatus.ACTIVE));

        given(labelRepository.findById(any(Long.class))).willReturn(expectedLabel);

        given(postRepository.findAllByLabelId(any(Long.class))).willReturn(expectedPosts);

        var expectedResult = labelDtoMapper.map(expectedLabel);

        expectedResult.setPosts(expectedPosts.stream()
            .map(postDtoMapper::map)
            .toList());

        var returnedDto = labelService.findById(any(Long.class));

        Assertions.assertEquals(expectedResult.getId(), returnedDto.getId());
        Assertions.assertEquals(expectedResult.getName(), returnedDto.getName());
        Assertions.assertEquals(expectedResult.getPosts(), returnedDto.getPosts());
    }

    @Test
    void testFindById_NotFound() {
        given(labelRepository.findById(any(Long.class))).willReturn(null);

        assertThrows(NotFoundException.class, () -> labelService.findById(any(Long.class)));
    }

    @Test
    void testFindById_Null() {
        assertThrows(NotFoundException.class, () -> labelService.findById(null));
    }

    @Test
    void testFindByName_Found() {
        var expectedDto = new LabelDto(10, "test", new ArrayList<>());

        given(labelRepository.findByName("test")).willReturn(labelMapper.map(expectedDto));

        var expectedPosts = new ArrayList<PostDto>();

        expectedPosts.add(new PostDto(
            1, 1, LocalDateTime.now(), LocalDateTime.now(),
            "123", PostStatus.ACTIVE));

        expectedDto.setPosts(expectedPosts);

        given(postRepository.findAllByLabelId(any(Long.class)))
            .willReturn(expectedPosts.stream()
                .map(postMapper::map).toList());

        var result = labelService.findByName("test");

        Assertions.assertEquals(expectedDto.getId(), result.getId());
        Assertions.assertEquals(expectedDto.getName(), result.getName());
        Assertions.assertEquals(expectedDto.getPosts(), result.getPosts());
    }

    @Test
    void testFindByName_NotFound() {
        given(labelRepository.findByName("test")).willReturn(null);

        assertThrows(NotFoundException.class, () -> labelService.findByName("test"));
    }

    @Test
    void testFindByName_Null() {
        assertThrows(NotFoundException.class, () -> labelService.findByName(null));
    }

    @Test
    void testFindAll() {
        var labels = new ArrayList<Label>();

        for (Long i = 1L; i < 4L; i++) {
            labels.add(Label.builder()
                .id(i)
                .name("test" + i)
                .build());
        }
        given(labelRepository.findAll()).willReturn(labels);

        var result = labelService.findAll();

        assertEquals(labels.stream().map(labelDtoMapper::map).toList(), result);
    }

    @Test
    void create() {
        var inputLabelDto = LabelDto.builder()
            .name("test")
            .build();

        var extendedResult = LabelDto.builder()
            .id(1L)
            .name(inputLabelDto.getName())
            .build();

        given(labelRepository.create(labelMapper.map(inputLabelDto)))
            .willReturn(labelMapper.map(extendedResult));

        var result = labelService.create(inputLabelDto);

        assertEquals(extendedResult.getName(), result.getName());
        assertEquals(extendedResult.getPosts(), result.getPosts());
        assertEquals(extendedResult.getId(), result.getId());
        assertNotNull(result.getId());
    }

    @Test
    void create_null() {
        assertThrows(NullPointerException.class, () -> labelService.create(null));
    }

    @Test
    void delete() {
        assertDoesNotThrow(() -> labelService.deleteById(any(Long.class)));
    }

    @Test
    void update() {
        var inputDto = new LabelDto(1, "test");

        given(labelRepository.update(labelMapper.map(inputDto)))
            .willReturn(labelMapper.map(inputDto));

        var returnedDto = labelService.update(inputDto);

        assertEquals(inputDto, returnedDto);
    }

    @Test
    void update_null() {
        assertThrows(NullPointerException.class, () -> labelService.update(null));
    }*/
}
