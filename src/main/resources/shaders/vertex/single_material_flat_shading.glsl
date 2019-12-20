#version 450

const int MAX_LIGHTS_COUNT = 64;

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 texCoord;

struct Material {
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    vec4 emissive;
    float density;
    float specular_exponent;
    float transparency;
    int illum;
};

struct PointLight {
    vec3 position;
    float a;
    vec4 color;
};

struct DirectionalLight {
    vec3 direction;
    float a;
    vec4 color;
};

struct SpotLight {
    vec3 position;
    float a;
    vec3 direction;
    float b;
    vec4 color;
    float cutoff;
};

layout(std140, binding = 1) uniform LightScene {
    int activePointLights;
    int activeDirectionalLights;
    int activeSpotLights;
    PointLight pointLights[MAX_LIGHTS_COUNT];
    DirectionalLight directionalLights[MAX_LIGHTS_COUNT];
    SpotLight spotLights[MAX_LIGHTS_COUNT];
} lightScene;

uniform Material material = {
    { 1, 1, 1, 1 },
    { 1, 1, 1, 1 },
    { 1, 1, 1, 1 },
    { 1, 1, 1, 1 },
    1,
    1,
    1,
    1
};

out flat vec4 oColor;
out vec3 oPosition;
out vec4 gl_Position;
out vec3 oNormal;

layout(std140, binding = 0) uniform ViewProjection {
    mat4 view;
    mat4 projection;
};

uniform mat4 model;

void main() {
    vec3 positionViewSpace = vec3(view * model * vec4(position, 1.0));
    vec3 normalViewSpace = inverse(transpose(mat3(view * model))) * normal;
    oNormal = normalViewSpace;

    gl_Position = projection * view * model * vec4(position, 1.0);
    oPosition = position;

    // ambient
    float ambientStrength = .1;
    float diffuseStrength = .8;
    float specularStrength = .05;

    vec4 ambient = ambientStrength * material.ambient;
    vec3 objectPosition = normalize(positionViewSpace);
    vec3 objectNormal = normalize(normalViewSpace);
    vec3 eye = -objectPosition; // vector eye -> object

    // In view space, all vertex with "negative" normals can be discarded

    vec4 res = ambient;
    for (int i = 0; i < lightScene.activePointLights; i++) {
        // diffuse
        vec3 lightToObject = normalize(lightScene.pointLights[i].position - positionViewSpace); // vector object -> light
        float lightToObjectAngle = max(dot(objectNormal, lightToObject), 0.0);
        vec4 diffuse = lightToObjectAngle * diffuseStrength * material.diffuse;

        // specular
        vec3 lightReflection = normalize(reflect(-lightToObject, objectNormal));// reflection of vector object -> light
        float lightReflectionToEye = pow(max(dot(eye, lightReflection), 0.0), 16);
        vec4 specular = lightReflectionToEye * specularStrength * material.specular * lightScene.pointLights[i].color;

        res += (diffuse + specular);
    }
    for (int i = 0; i < lightScene.activeDirectionalLights; i++) {
        // diffuse
        float lightToObjectAngle = max(dot(objectNormal, lightScene.directionalLights[i].direction), 0.0);
        vec4 diffuse = lightToObjectAngle * diffuseStrength * material.diffuse * lightScene.directionalLights[i].color;

        // specular
        vec3 lightReflection = normalize(reflect(-lightScene.directionalLights[i].direction, objectNormal));// reflection of vector object -> light
        float lightReflectionToEye = pow(max(dot(eye, lightReflection), 0.0), 16);
        vec4 specular = lightReflectionToEye * specularStrength * material.specular * lightScene.directionalLights[i].color;

        res += (diffuse + specular);
    }

    oColor = res;
}
