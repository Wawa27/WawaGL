#version 450

struct Light {
    vec3 position;
    vec3 direction;
    vec3 color;
};

in vec3 aPosition;
in vec3 aNormal;
in vec2 aTexCoord;

out vec4 gl_FragColor;

uniform sampler2D kd_texture;

uniform Light light = {
    vec3(0, 2, 0),
    vec3(1, -1, 0),
    vec3(1, 1, 1)
};

void main() {
    vec3 normal = normalize(aNormal);
    float distance = length(lightPos - aPosition);
    float attenuation = 1.0 / (1.0 + 0.14	 * distance +
    0.07 * (distance * distance));

    // ambient
    float ambientStrength = 0.1;
    vec3 ambient = ambientStrength * texture(kd_texture, aTexCoord).xyz;

    // diffuse
    vec3 lightToObject = normalize(lightPos - aPosition);
    float lightToObjectAngle = max(dot(normal, lightToObject), 0.0);
    vec3 diffuse = lightToObjectAngle * texture(kd_texture, aTexCoord).xyz;

    // specular
    float specularStrength = 0.5;
    vec3 eye = normalize(-aPosition);// eye is the vector eye -> object
    vec3 r = reflect(-lightToObject, normal);// r is the reflection of vector object -> light
    float reflectionToEye = pow(max(dot(eye, r), 0.0), 32);
    vec3 specular = specularStrength * reflectionToEye * texture(kd_texture, aTexCoord).xyz;

    vec3 res =
    ambient * light.color
    +   diffuse * light.color
    +   specular * vec3(1, 0, 0);
    gl_FragColor = vec4(res, 1.0);
}
